
v3.1.0

alter table u_user modify status varchar(50);
update u_user set status='ACTIVE' where status='100';

update u_user set status='INACTIVE' where status='0';

update u_user set status='LOCKED' where status='2';

alter table u_member modify status varchar(50);
update u_member set status='ACTIVE' where status='100';

update u_member set status='INACTIVE' where status='0';

update u_member set status='LOCKED' where status='2';

alter table u_department modify type varchar(50);
alter table u_department add code varchar(50);
update u_department set type='Department' where type='0'
update u_department set type='Organization' where type='1'

alter table u_member change birth birthday date;
alter table u_member change reg_date reg_time date;
alter table u_member change gender sex varchar(3);
alter table u_member change mobile mobile_no varchar(50);
alter table u_member change tel tel_no varchar(50);
alter table u_member add info varchar(500);
alter table u_member add order_no int;
