# --- Sample dataset

# --- !Ups

insert into sale (id,userId,modelId, imageId, year, price, mileage) values ( 1, 1, 1, 1, '2002-01-02', 10020, 202239);

# --- !Downs

delete from sale;
