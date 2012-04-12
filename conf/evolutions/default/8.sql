# --- Sample dataset

# --- !Ups

insert into salecomment (id, userId, saleId, text, accepted) values (1, 1, 1, 'Test comment', true);

# --- !Downs

delete from salecomment
