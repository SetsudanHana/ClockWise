--NAGLOWEK I STOPKA
INSERT INTO mail_template(id, mail_type, content, subject) VALUES(1, 'HEADER', '<html><head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><title>Parcel Tracker</title></head><body><div style="font-family: sans-serif, serif, Garamond;height: 475px; border: 3px groove;border-color: #22B14C; width: 500px; margin: 0 auto;"><div style="width: 500px;"><div style="text-align: center"><img src=""></img><p style="font-size: medium; margin: 0px; padding-top:20px;"><b>Clockwise</b></p><p style="font-size: 12px; margin-bottom: 20px;">System do monitorowania Twojej pracy!</p></div></div>', '');
INSERT INTO mail_template(id, mail_type, content, subject) VALUES(2, 'FOOTER', '<p><footer style="height: 100px; clear: both; position: relative; z-index: 10; height: 3em; margin-top: 2em; text-align: center; font-size: 10px"><center><a href="https://github.com/SetsudanHana/ClockWise">Clockwise</a> - system do monitorowania czasu pracy. <p>Wszelkie prawa zastrzeżone</p><p>Developed by PussyDolls@2016</center></p><footer></p></div></body></html>', '');

--TRESCI
INSERT INTO mail_template(id, mail_type, content, subject) VALUES(3, 'NEW_USER_REGISTERED', '<div style="margin: 0 auto; border: 1px groove; border-color: #FFC90E;width: 475px; background-color: #FFC90E "><div style="margin: 0 auto; border: 1px groove; border-top:none;border-color: #22B14C; width: 450px; background-color: #22B14C"><h3 style="text-align: center; margin: 0px">Użytkownik został zarejestrowany</h3></div><table style="margin: 0 auto; margin-top: 50px; margin-bottom: 50px;"><tr style="line-height: 25px"><td><b>Użytkownik:</b></td><td style="text-align: left">${username}</td></tr><tr style="line-height: 25px"><td><b>Email:</b></td><td> ${email}</td></tr><tr style="line-height: 25px"><td><b>Link aktywacyjny:</b></td><td> ${link}</td></tr></table></div>', 'Clockwise - rejestracja w systemie');
INSERT INTO mail_template(id, mail_type, content, subject) VALUES(4, 'USER_PASSWORD_UPDATE', '<div style="margin: 0 auto; border: 1px groove; border-color: #FFC90E;width: 475px; background-color: #FFC90E "><div style="margin: 0 auto; border: 1px groove; border-top:none;border-color: #22B14C; width: 450px; background-color: #22B14C"><h3 style="text-align: center; margin: 0px">Twoje hasło zostało pomyślnie zmienione</h3></div><table style="margin: 0 auto; margin-top: 50px; margin-bottom: 50px;"><td><center><p>Hasło dla użytkownika ${username} zostało pomyślnie zmienione. Aby zalogować się do systemu przejdź na stronę<a href="https://github.com/SetsudanHana/ClockWise">Clockwise</a></p></center></td></table></div>','Clockwise - zmiana hasła użytkownika');
INSERT INTO mail_template(id, mail_type, content, subject) VALUES(5, 'USER_PASSWORD_RESET', '	<div style="margin: 0 auto; border: 1px groove; border-color: #FFC90E;width: 475px; background-color: #FFC90E "><div style="margin: 0 auto; border: 1px groove; border-top:none;border-color: #22B14C; width: 450px; background-color: #22B14C"><h3 style="text-align: center; margin: 0px">Twoje hasło zostało zresetowane</h3></div><table style="margin: 0 auto; margin-top: 50px; margin-bottom: 50px;"><tr style="line-height: 25px"><td><p>Nowe hasło zostało wygenerowane automatycznie.</p>Przejdź do swojego profilu aby zmienić hasło.</tr><tr style="line-height: 25px"><td>Użytkownik: ${username}</td></tr><tr style="line-height: 25px"><td>Nowe hasło: ${password}</td></tr></table></div>', 'Clockwise - hasło zostało zresetowane');
INSERT INTO mail_template(id, mail_type, content, subject) VALUES(6, 'NEW_COMPANY_CREATED_AND_NOT_ACTIVATED', '	<div style="margin: 0 auto; border: 1px groove; border-color: #FFC90E;width: 475px; background-color: #FFC90E "><div style="margin: 0 auto; border: 1px groove; border-top:none;border-color: #22B14C; width: 450px; background-color: #22B14C"><h3 style="text-align: center; margin: 0px">Nowa firma - <i>${company}</i> została utworzona</h3></div><table style="margin: 0 auto; margin-top: 50px; margin-bottom: 50px;"><tr style="line-height: 25px"><td><p>Stworzono nową firmę o nazwie <i>${company}</i></p>Przejdź do linku poniżej aby aktywować firmę.</tr><tr style="line-height: 25px"><td><a href="${link}">AKTYWUJ FIRMĘ</a></td></tr></table></div>', 'Clockwise - utworzono firmę bez aktywacji');
INSERT INTO mail_template(id, mail_type, content, subject) VALUES(7, 'NEW_COMPANY_CREATED_AND_ACTIVATED', '	<div style="margin: 0 auto; border: 1px groove; border-color: #FFC90E;width: 475px; background-color: #FFC90E "><div style="margin: 0 auto; border: 1px groove; border-top:none;border-color: #22B14C; width: 450px; background-color: #22B14C"><h3 style="text-align: center; margin: 0px">Nowa firma - <i>${company}</i> została utworzona</h3></div><table style="margin: 0 auto; margin-top: 50px; margin-bottom: 50px;"><tr style="line-height: 25px"><td><p>Stworzono nową firmę o nazwie <i>${company}</i></p>Super admin dla firmy został utworzony automatycznie.</tr></table></div>', 'Clockwise - utworzono i aktywowano firmę');

--COMPANY
INSERT INTO company(id, name, status, email) VALUES(2, 'Firma', 'ACTIVE', 'firma@firma.pl');

--USER
INSERT INTO users(id, username, password, email, role, status, company_id ) VALUES(3, 'Testowy', 'haselko3!', 'mail@mailik.com', 2, 'ACTIVE', 2);

--STATISTICS
INSERT INTO statistic(id, mouse_clicked_count, keyboard_clicked_count, mouse_movement_count, date, user_id) VALUES(1, 231, 321, 234, '2016-05-02', 3);
INSERT INTO statistic(id, mouse_clicked_count, keyboard_clicked_count, mouse_movement_count, date, user_id) VALUES(2, 102, 963, 745, '2016-07-15', 3);
INSERT INTO statistic(id, mouse_clicked_count, keyboard_clicked_count, mouse_movement_count, date, user_id) VALUES(3, 100, 987, 456, '2016-09-20', 3);
INSERT INTO statistic(id, mouse_clicked_count, keyboard_clicked_count, mouse_movement_count, date, user_id) VALUES(4, 326, 621, 895, '2016-11-11', 3);
