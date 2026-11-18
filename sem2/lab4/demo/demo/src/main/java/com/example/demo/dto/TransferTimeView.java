package com.example.demo.dto;

public class TransferTimeView {

    private final AppointmentDetailsView currentAppointmentDetailsView;
    private final TimeSelectView currentTimeSelectView;

    public TransferTimeView(AppointmentDetailsView currentAppointmentDetailsView, TimeSelectView currentTimeSelectView) {
        this.currentAppointmentDetailsView = currentAppointmentDetailsView;
        this.currentTimeSelectView = currentTimeSelectView;
    }

    public AppointmentDetailsView getCurrentAppointmentDetailsView() {
        return currentAppointmentDetailsView;
    }

    public TimeSelectView getCurrentTimeSelectView() {
        return currentTimeSelectView;
    }
}
