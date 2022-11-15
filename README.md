# Room 
Android Room is a ORM(object relational mapping) library created for SQLite. SQLite is the database management system we use to create databases in Android projects.

Before 2017, we had to write a lot of complex codes for data base operations. But now, Room recognises our requirements (considering annotations) and generates most of the codes(in byte code format/class files) for us.

Room acts as a layer on top of SQLite.  It makes our coding lives much easier and efficient.

## Must have components for Android Room
To use Room library/framework, you need 3 code components. Entity classes, Dao interface and the database class.

## 1) Room Entity Classes.
### One entity class for each table
* We need to create entity classes for each database table.

* Moreover, to qualify a class as a room entity class, we need to annotate it with @Entity .

* Holding data is the only purpose of these classes. So, we will create them as Kotlin data classes.

### Name of the database table
* Database table will have the same name as the class name.

* And, if we need a different name, we could give that name as the value of the “tableName” property.

### Name of the table columns
Columns will have same names as the data class’s variable names.

But, if we want different names we can provide them using @ColumnInfo

### Primary Key
* Use the @PrimaryKey on the variable selected as the primary key of the table.

* If you want the primary key to auto generate set autoGenerate = true



## 2) Room DAO interface
We need to create an interface . And, mark it with @Dao .

DAO stands for “Data Access Object”. This interface is where we define functions to deal with the database.

(you could also define DAO as an abstract class)

### Function names
Function names are not important. You can give any name you like.

### Annotations
But, annotating the function with correct annotation is very important.

For instance, we have annotated above “insertBook” function(method) with @Insert . Therefore, room will know that the purpose of that function is inserting data. Room library does not understand the function names. But, room recognizes annotations.

### Suspend keyword
“Kotlin coroutines” is the current best practice to manage threads in android development. So, we are going to use them to execute database operations in a background thread. Therefore, we need to define these functions as suspending functions.

But, it is not required for query functions. Because, Room library uses its own dispatcher to run queries on a background thread.

### SQL Statement
For basic Insert, Update and Delete functions we don’t need to write SQL statements.

But, we need to write a SQL statement for Query functions and for customized Update and Delete functions.

### Room Insert functions
* All insert functions should annotate with @Insert

* There is no need to write a SQL query for insert functions.

* Return type is optional. Most of the time we write room insert functions without a return type.

* But, Room allows us to get the newly inserted row id as a “Long” value.

* For example, if the insert function has a single parameter, return type would be the new row id of type Long .

* On the other hand, if the parameter is an array or a collection, return type would be an array or a collection of Long values.

### Room Update and Delete functions
* Update functions should annotate with @Update and delete functions should annotate with @Delete .

* These functions also don’t require a SQL statement. And, return types are optional.

* But, we can add an “int” return type.

* These functions return an “int" value indicating the number of rows that were updated or deleted successfully

## 3) Room Database Class
We need to create a Room Database class.

This class should be an abstract class. Most importantly, this should extend the “RoomDatabase” class.

We need to annotate this class with @Database .

Then, provide the list of entity classes with it.

And, also provide the version number . Database’s version number is very important when we are going to migrate the database.

Finally, we need to define abstract functions to get Dao interfaces inside the class.
