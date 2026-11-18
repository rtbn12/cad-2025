package com.example.demo.dto;


public class TransferAppointmentPreview {

    private final AppointmentDetailsView oldAppointmentDetailsView;
    private final  AppointmentPreviewView appointmentPreviewView;

    public TransferAppointmentPreview(AppointmentDetailsView oldAppointmentDetailsView,
                                      AppointmentPreviewView appointmentPreviewView) {

        this.oldAppointmentDetailsView = oldAppointmentDetailsView;
        this.appointmentPreviewView = appointmentPreviewView;
    }

    public AppointmentDetailsView getOldAppointmentDetailsView() {
        return oldAppointmentDetailsView;
    }

    public AppointmentPreviewView getAppointmentPreviewView() {
        return appointmentPreviewView;
    }
}
