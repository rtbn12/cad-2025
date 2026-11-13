package com.example.demo.dto;

import com.example.demo.entity.SlotStatus;

import java.time.LocalTime;

public class TimeView {

    private final Long slotId;
    private final LocalTime start;
    private final LocalTime end;
    private final SlotStatus status;
    private final boolean available;

    public TimeView(Long slotId, LocalTime start,
                    LocalTime end, SlotStatus status, boolean available) {
        this.slotId = slotId;
        this.start = start;
        this.end = end;
        this.status = status;
        this.available = available;
    }

    public Long getSlotId() {
        return slotId;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public SlotStatus getStatus() {
        return status;
    }

    public boolean isAvailable() {
        return available;
    }
}
