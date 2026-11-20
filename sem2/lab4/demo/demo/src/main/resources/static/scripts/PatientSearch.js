(() => {
 const field = document.getElementById('search-value');
 const label = document.querySelector('.search-value-label');
 const hint = document.getElementById('search-hint');
 const modes = document.querySelectorAll('input[name="searchType"]');
 function update() {
  const phone = document.querySelector('input[name="searchType"]:checked')?.value === 'PHONE';
  label.textContent = phone ? 'Номер телефона' : 'Номер полиса ОМС';
  field.placeholder = phone ? '+7-900-123-45-67' : '16 цифр без пробелов';
  field.inputMode = phone ? 'tel' : 'numeric';
  hint.textContent = phone ? 'Введите телефон в формате +7-900-123-45-67.' : 'Введите ровно 16 цифр полиса ОМС.';
 }
 modes.forEach(mode => mode.addEventListener('change', () => {field.value = ''; update();}));
 update();
})();
