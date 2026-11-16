package com.example.demo.dto;

import java.util.List;

public class TransferAppointmentView {

    private final AppointmentDetailsView appointmentDetailsView;
    private final List<DateView> dateViews;

    public TransferAppointmentView(AppointmentDetailsView appointmentDetailsView,
                                   List<DateView> dateViews) {

        this.appointmentDetailsView = appointmentDetailsView;
        this.dateViews = dateViews;
    }

    public AppointmentDetailsView getAppointmentDetailsView() {
        return appointmentDetailsView;
    }

    public List<DateView> getDateViews() {
        return dateViews;
    }
}
