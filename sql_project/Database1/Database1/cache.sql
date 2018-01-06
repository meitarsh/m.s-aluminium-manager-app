CREATE TABLE [dbo].[cache]
(
	[Id] INT NOT NULL PRIMARY KEY IDENTITY(1,1), 
    [command] TEXT NOT NULL, 
    [user] TEXT NOT NULL

)
