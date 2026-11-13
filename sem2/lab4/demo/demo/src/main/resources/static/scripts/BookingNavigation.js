/* Только удобство возврата. Выбор и права доступа по-прежнему проверяет сервер. */
(() => {
    const key = 'booking.lastSpecialityId';
    try {
        const context = document.querySelector('[data-booking-speciality]');
        if (context && /^[1-9]\d*$/.test(context.dataset.bookingSpeciality)) {
            sessionStorage.setItem(key, context.dataset.bookingSpeciality);
        }
        const doctorContext = document.querySelector('[data-booking-doctor]');
        if (doctorContext && /^[1-9]\d*$/.test(doctorContext.dataset.bookingDoctor)) {
            sessionStorage.setItem('booking.lastDoctorId', doctorContext.dataset.bookingDoctor);
        }
        const error = document.querySelector('[data-error-path]');
        if (error?.dataset.errorPath === '/cabinet/createAppointment/setTime') {
            const doctorId = sessionStorage.getItem('booking.lastDoctorId');
            const back = document.getElementById('error-back');
            const label = document.getElementById('error-back-label');
            if (back && label && /^[1-9]\d*$/.test(doctorId || '')) {
                const target = new URL(back.href);
                target.pathname = target.pathname.replace(/setSpeciality$/, 'setDate');
                target.searchParams.set('selectDoctorId', doctorId);
                back.href = target.href;
                label.textContent = 'К выбору даты';
            }
        }
        const specialityId = sessionStorage.getItem(key);
        if (error?.dataset.errorPath === '/cabinet/createAppointment/setDate'
                && /^[1-9]\d*$/.test(specialityId || '')) {
            const back = document.getElementById('error-back');
            const label = document.getElementById('error-back-label');
            if (back && label) {
                const target = new URL(back.href);
                target.pathname = target.pathname.replace(/setSpeciality$/, 'setDoctor');
                target.searchParams.set('specialityId', specialityId);
                back.href = target.href;
                label.textContent = 'К выбору врача';
            }
        }
    } catch {
        // Если хранилище недоступно, остаётся обычная ссылка к специальностям.
    }
})();
