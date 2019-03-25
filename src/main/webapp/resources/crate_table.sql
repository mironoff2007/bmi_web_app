DROP TABLE IF EXISTS bmi;

CREATE TABLE "bmi" (
  

  bmi float DEFAULT NULL,
  
  name varchar DEFAULT NULL,

  weight int  Default 1, 
  
  height int Default 1,

  dateTimeStep bigint Default 1
) ;


INSERT INTO "bmi" VALUES 
	(2.0515087,'Vasja',65,178,1553518802929),
	(2.3529413,'Petja',68,170,1553518802960);