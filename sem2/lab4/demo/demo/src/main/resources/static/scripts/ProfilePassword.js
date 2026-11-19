(() => {
    const toggle = document.getElementById('change-password');
    const fields = ['new-password', 'confirm-password'].map(id => document.getElementById(id));
    function update() {
        fields.forEach(field => {
            field.value = '';
            field.disabled = !toggle.checked;
            field.required = toggle.checked;
            field.minLength = 10;
            field.maxLength = 72;
            const passwordPattern = '(?=.*[A-Z])(?=.*[!@#$%^&*()_+=?.,:;~-])[A-Za-z0-9!@#$%^&*()_+=?.,:;~-]{10,72}';
            field.oninput = () => field.setCustomValidity(field.value && !new RegExp('^(?:' + passwordPattern + ')$').test(field.value) ? 'От 10 до 72 символов, латиница, заглавная буква и спецсимвол; без пробелов' : '');
            field.setCustomValidity('');
            field.placeholder = toggle.checked ? '' : '\u2022\u2022\u2022\u2022\u2022\u2022\u2022\u2022\u2022\u2022';
        });
    }
    toggle.addEventListener('change', update);
    update();
})();
