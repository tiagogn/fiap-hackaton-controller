
alter table upload add column zip_file text;

create type video_status as enum ('PENDING', 'PROCESSING', 'PROCESSED', 'ERROR');

alter table video add column status video_status;