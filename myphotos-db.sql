create sequence profile_seq;

alter sequence profile_seq owner to myphotos;

create sequence photo_seq;

alter sequence photo_seq owner to myphotos;

create table profile
(
	avatar_url varchar(255) not null,
	created timestamp default now() not null,
	email varchar(100) not null
		constraint profile_email
			unique,
	first_name varchar(60) not null,
	id bigint not null
		constraint profile_pkey
			primary key,
	job_title varchar(100) not null,
	last_name varchar(60) not null,
	location varchar(100) not null,
	photo_count integer default 0 not null,
	uid varchar(255) not null
		constraint profile_uid
			unique,
	rating integer default 0 not null
);

alter table profile owner to myphotos;

create table photo
(
	id bigint not null
		constraint photo_pkey
			primary key,
	profile_id bigint not null
		constraint photo_profile_id_fk
			references profile
				on update restrict on delete restrict,
	small_url varchar(255) not null,
	large_url varchar(255) not null,
	original_url varchar(255) not null,
	views bigint default 0 not null,
	downloads bigint default 0 not null,
	created timestamp default now() not null
);

alter table photo owner to myphotos;

create index fki_photo_profile_id_fk
	on photo (profile_id);

create table access_token
(
	token varchar(512) not null
		constraint access_token_pkey
			primary key,
	profile_id bigint not null
		constraint access_token_profile_id_fk
			references profile
				on update restrict on delete restrict,
	created timestamp default now()
);

alter table access_token owner to myphotos;

create index fki_access_token_profile_id_fk
	on access_token (profile_id);

