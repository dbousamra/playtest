# --- Sample dataset

# --- !Ups

insert into sale_comment (id, user_id, sale_id, text, accepted) values (1, 1, 1, 'Test comment', true);

# --- !Downs

delete from sale_comment
