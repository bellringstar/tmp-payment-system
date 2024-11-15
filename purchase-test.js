import http from 'k6/http';
import {check, sleep} from 'k6';
import {randomIntBetween} from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';
import {textSummary} from 'https://jslib.k6.io/k6-summary/0.0.2/index.js';
import {Counter, Rate} from 'k6/metrics';

// 사용자 정의 메트릭
const successRate = new Rate('success_rate');
const errorRate = new Rate('error_rate');
const totalRequests = new Counter('total_requests');

export const options = {
    stages: [
        {duration: '30s', target: 100},  // 30초 동안 명까지 증가
        {duration: '1m', target: 100},   // 1분 동안 명 유지
        {duration: '30s', target: 200},  // 30초 동안 명까지 증가
        {duration: '1m', target: 200},   // 1분 동안 명 유지
        {duration: '30s', target: 0},     // 30초 동안 0명으로 감소
    ],
    thresholds: {
        http_req_duration: ['p(99)<1000'],  // 95%의 요청이 500ms 이하, 99%는 1000ms 이하
        http_req_failed: ['rate<0.001'],                 // 실패율 0.1% 미만
        success_rate: ['rate>0.999'],                    // 성공률 99.9% 이상
    },
};

const BASE_URL = 'http://payment-api:8080';

function createPurchasePayload() {
    return {
        memberId: randomIntBetween(1, 1000),
        ticketId: randomIntBetween(1, 10),
        ticketStockId: randomIntBetween(1, 1000)
    };
}

export default function () {
    totalRequests.add(1);
    const festivalId = randomIntBetween(1, 5);
    const ticketId = randomIntBetween(1, 10);
    const payload = createPurchasePayload();

    const headers = {
        'Content-Type': 'application/json',
    };

    const url = `${BASE_URL}/api/v1/festivals/${festivalId}/tickets/${ticketId}/purchase`;

    const response = http.post(url, JSON.stringify(payload), {
        headers: headers,
        tags: {name: 'PurchaseTicket'},
    });

    const success = check(response, {
        'status is 200': (r) => r.status === 200,
        'response time < 2000ms': (r) => r.timings.duration < 2000,
    });

    successRate.add(success);
    errorRate.add(!success);

    sleep(0.1);
}

export function handleSummary(data) {
    // data.state 내용 확인을 위한 로깅 추가
    console.log('Test State:', JSON.stringify(data.state, null, 2));

    const getSafeValue = (metric, path) => {
        if (!metric || !metric.values) return 0;
        return metric.values[path] || 0;
    };

    const getSafeMetric = (metrics, name) => {
        return metrics[name] || {values: {}};
    };

    // 시작 시간과 종료 시간을 이용해 직접 계산
    const testDuration = data.state.testRunDurationMs / 1000; // 초 단위로 변환
    const iterations = getSafeMetric(data.metrics, 'iterations');
    const errors = getSafeMetric(data.metrics, 'errors');
    const duration = getSafeMetric(data.metrics, 'http_req_duration');
    const reqs = getSafeMetric(data.metrics, 'http_reqs');
    const dataSent = getSafeMetric(data.metrics, 'data_sent');

    const summary = {
        "테스트 요약": {
            "날짜/시간": new Date().toISOString(),
            "총 소요 시간": `${testDuration.toFixed(2)}초`,
            "총 요청 수": getSafeValue(iterations, 'count'),
            "성공한 요청": getSafeValue(iterations, 'count') - getSafeValue(errors, 'count'),
            "실패한 요청": getSafeValue(errors, 'count'),
            "성공률": `${((1 - getSafeValue(errors, 'rate')) * 100).toFixed(2)}%`,
        },
        "응답 시간": {
            "최소": `${getSafeValue(duration, 'min').toFixed(2)}ms`,
            "최대": `${getSafeValue(duration, 'max').toFixed(2)}ms`,
            "평균": `${getSafeValue(duration, 'avg').toFixed(2)}ms`,
            "중간값": `${getSafeValue(duration, 'med').toFixed(2)}ms`,
            "95퍼센타일": `${getSafeValue(duration, 'p(95)').toFixed(2)}ms`,
        },
        "처리량": {
            "초당 요청 수": testDuration > 0 ? (getSafeValue(reqs, 'count') / testDuration).toFixed(2) : "0",
            "초당 데이터 전송량": testDuration > 0 ?
                `${(getSafeValue(dataSent, 'count') / testDuration).toFixed(2)} bytes/sec` :
                "0 bytes/sec"
        }
    };

    return {
        stdout: JSON.stringify(summary, null, 2),
        'summary.json': JSON.stringify(summary, null, 2),
        'summary.txt': textSummary(data, {indent: ' ', enableColors: true})
    };
}