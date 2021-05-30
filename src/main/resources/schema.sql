drop table player_credit IF EXISTS;
drop table item IF EXISTS;
drop table player_inventory IF EXISTS;

create TABLE player_credit (
	playerId	BIGINT	NOT NULL PRIMARY KEY,
	paidCredit	DECIMAL(18,2)	NULL,
	freeCredit	DECIMAL(18,2)	NULL
);

create TABLE item (
	itemId	BIGINT	NOT NULL PRIMARY KEY,
	price	DECIMAL(18,2)	NOT NULL
);

create TABLE player_inventory (
	playerId	BIGINT	NOT NULL,
	itemId	BIGINT	NOT NULL,
	quantity	INTEGER	NOT NULL,
	PRIMARY KEY(playerId, itemId)
);