package com.example.worker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "checkin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Checkin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checkin_id")
    private Long id;

    @JoinColumn(name = "festival_id", nullable = false, updatable = false)
    private Long festivalId;

    @JoinColumn(name = "member_id", nullable = false, updatable = false)
    private Long memberId;

    @JoinColumn(name = "ticket_id", nullable = false, updatable = false)
    private Long ticketId;

    @Column(name = "checkin_time")
    private LocalDateTime checkinTime; // 체크인하지 않은 경우 null

    @Column(name = "is_checked", nullable = false)
    private boolean isChecked;

    private Checkin(Long festivalId, Long memberId, Long ticketId, LocalDateTime checkinTime, boolean isChecked) {
        this.festivalId = festivalId;
        this.memberId = memberId;
        this.ticketId = ticketId;
        this.checkinTime = checkinTime;
        this.isChecked = isChecked;
    }

    public static Checkin create(Long festivalId, Long memberId, Long ticketId) {
        return new Checkin(festivalId, memberId, ticketId, LocalDateTime.now(), false);
    }

    public void updateCheckedIn() {
        // 이미 체크인한 경우 에러 발생
        if (isCheckedIn()) {
            throw new RuntimeException("Already check in");
        }

        isChecked = true;
        checkinTime = LocalDateTime.now();
    }

    public boolean isCheckedIn() {
        return isChecked && checkinTime != null;
    }
}