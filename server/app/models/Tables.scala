package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.MySQLProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Array(Event.schema, Pet.schema, Stats.schema, User.schema, Userdate.schema, Userevent.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Event
   *  @param id Database column ID SqlType(INT), AutoInc, PrimaryKey
   *  @param message Database column Message SqlType(TEXT)
   *  @param probability Database column Probability SqlType(FLOAT)
   *  @param moneyinc Database column MoneyInc SqlType(INT)
   *  @param affectioninc Database column AffectionInc SqlType(INT)
   *  @param hungerinc Database column HungerInc SqlType(INT)
   *  @param exhaustioninc Database column ExhaustionInc SqlType(INT) */
  case class EventRow(id: Int, message: String, probability: Float, moneyinc: Int, affectioninc: Int, hungerinc: Int, exhaustioninc: Int)
  /** GetResult implicit for fetching EventRow objects using plain SQL queries */
  implicit def GetResultEventRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Float]): GR[EventRow] = GR{
    prs => import prs._
    EventRow.tupled((<<[Int], <<[String], <<[Float], <<[Int], <<[Int], <<[Int], <<[Int]))
  }
  /** Table description of table Event. Objects of this class serve as prototypes for rows in queries. */
  class Event(_tableTag: Tag) extends profile.api.Table[EventRow](_tableTag, Some("virtualpet"), "Event") {
    def * = (id, message, probability, moneyinc, affectioninc, hungerinc, exhaustioninc) <> (EventRow.tupled, EventRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(message), Rep.Some(probability), Rep.Some(moneyinc), Rep.Some(affectioninc), Rep.Some(hungerinc), Rep.Some(exhaustioninc))).shaped.<>({r=>import r._; _1.map(_=> EventRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column Message SqlType(TEXT) */
    val message: Rep[String] = column[String]("Message")
    /** Database column Probability SqlType(FLOAT) */
    val probability: Rep[Float] = column[Float]("Probability")
    /** Database column MoneyInc SqlType(INT) */
    val moneyinc: Rep[Int] = column[Int]("MoneyInc")
    /** Database column AffectionInc SqlType(INT) */
    val affectioninc: Rep[Int] = column[Int]("AffectionInc")
    /** Database column HungerInc SqlType(INT) */
    val hungerinc: Rep[Int] = column[Int]("HungerInc")
    /** Database column ExhaustionInc SqlType(INT) */
    val exhaustioninc: Rep[Int] = column[Int]("ExhaustionInc")
  }
  /** Collection-like TableQuery object for table Event */
  lazy val Event = new TableQuery(tag => new Event(tag))

  /** Entity class storing rows of table Pet
   *  @param id Database column ID SqlType(INT), AutoInc, PrimaryKey
   *  @param userid Database column UserID SqlType(INT)
   *  @param name Database column Name SqlType(VARCHAR), Length(50,true)
   *  @param adoptiondate Database column AdoptionDate SqlType(DATE)
   *  @param icon Database column Icon SqlType(INT) */
  case class PetRow(id: Int, userid: Int, name: String, adoptiondate: java.sql.Date, icon: Int)
  /** GetResult implicit for fetching PetRow objects using plain SQL queries */
  implicit def GetResultPetRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Date]): GR[PetRow] = GR{
    prs => import prs._
    PetRow.tupled((<<[Int], <<[Int], <<[String], <<[java.sql.Date], <<[Int]))
  }
  /** Table description of table Pet. Objects of this class serve as prototypes for rows in queries. */
  class Pet(_tableTag: Tag) extends profile.api.Table[PetRow](_tableTag, Some("virtualpet"), "Pet") {
    def * = (id, userid, name, adoptiondate, icon) <> (PetRow.tupled, PetRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(userid), Rep.Some(name), Rep.Some(adoptiondate), Rep.Some(icon))).shaped.<>({r=>import r._; _1.map(_=> PetRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column UserID SqlType(INT) */
    val userid: Rep[Int] = column[Int]("UserID")
    /** Database column Name SqlType(VARCHAR), Length(50,true) */
    val name: Rep[String] = column[String]("Name", O.Length(50,varying=true))
    /** Database column AdoptionDate SqlType(DATE) */
    val adoptiondate: Rep[java.sql.Date] = column[java.sql.Date]("AdoptionDate")
    /** Database column Icon SqlType(INT) */
    val icon: Rep[Int] = column[Int]("Icon")
  }
  /** Collection-like TableQuery object for table Pet */
  lazy val Pet = new TableQuery(tag => new Pet(tag))

  /** Entity class storing rows of table Stats
   *  @param id Database column ID SqlType(INT), AutoInc, PrimaryKey
   *  @param petid Database column PetID SqlType(INT)
   *  @param affection Database column Affection SqlType(INT)
   *  @param hunger Database column Hunger SqlType(INT)
   *  @param exhaustion Database column Exhaustion SqlType(INT)
   *  @param money Database column Money SqlType(INT)
   *  @param statdate Database column StatDate SqlType(DATE) */
  case class StatsRow(id: Int, petid: Int, affection: Int, hunger: Int, exhaustion: Int, money: Int, statdate: java.sql.Date)
  /** GetResult implicit for fetching StatsRow objects using plain SQL queries */
  implicit def GetResultStatsRow(implicit e0: GR[Int], e1: GR[java.sql.Date]): GR[StatsRow] = GR{
    prs => import prs._
    StatsRow.tupled((<<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[java.sql.Date]))
  }
  /** Table description of table Stats. Objects of this class serve as prototypes for rows in queries. */
  class Stats(_tableTag: Tag) extends profile.api.Table[StatsRow](_tableTag, Some("virtualpet"), "Stats") {
    def * = (id, petid, affection, hunger, exhaustion, money, statdate) <> (StatsRow.tupled, StatsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(petid), Rep.Some(affection), Rep.Some(hunger), Rep.Some(exhaustion), Rep.Some(money), Rep.Some(statdate))).shaped.<>({r=>import r._; _1.map(_=> StatsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column PetID SqlType(INT) */
    val petid: Rep[Int] = column[Int]("PetID")
    /** Database column Affection SqlType(INT) */
    val affection: Rep[Int] = column[Int]("Affection")
    /** Database column Hunger SqlType(INT) */
    val hunger: Rep[Int] = column[Int]("Hunger")
    /** Database column Exhaustion SqlType(INT) */
    val exhaustion: Rep[Int] = column[Int]("Exhaustion")
    /** Database column Money SqlType(INT) */
    val money: Rep[Int] = column[Int]("Money")
    /** Database column StatDate SqlType(DATE) */
    val statdate: Rep[java.sql.Date] = column[java.sql.Date]("StatDate")
  }
  /** Collection-like TableQuery object for table Stats */
  lazy val Stats = new TableQuery(tag => new Stats(tag))

  /** Entity class storing rows of table User
   *  @param id Database column ID SqlType(INT), AutoInc, PrimaryKey
   *  @param username Database column Username SqlType(VARCHAR), Length(50,true)
   *  @param password Database column Password SqlType(VARCHAR), Length(50,true)
   *  @param creationdate Database column CreationDate SqlType(DATE) */
  case class UserRow(id: Int, username: String, password: String, creationdate: java.sql.Date)
  /** GetResult implicit for fetching UserRow objects using plain SQL queries */
  implicit def GetResultUserRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Date]): GR[UserRow] = GR{
    prs => import prs._
    UserRow.tupled((<<[Int], <<[String], <<[String], <<[java.sql.Date]))
  }
  /** Table description of table User. Objects of this class serve as prototypes for rows in queries. */
  class User(_tableTag: Tag) extends profile.api.Table[UserRow](_tableTag, Some("virtualpet"), "User") {
    def * = (id, username, password, creationdate) <> (UserRow.tupled, UserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(username), Rep.Some(password), Rep.Some(creationdate))).shaped.<>({r=>import r._; _1.map(_=> UserRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column Username SqlType(VARCHAR), Length(50,true) */
    val username: Rep[String] = column[String]("Username", O.Length(50,varying=true))
    /** Database column Password SqlType(VARCHAR), Length(50,true) */
    val password: Rep[String] = column[String]("Password", O.Length(50,varying=true))
    /** Database column CreationDate SqlType(DATE) */
    val creationdate: Rep[java.sql.Date] = column[java.sql.Date]("CreationDate")
  }
  /** Collection-like TableQuery object for table User */
  lazy val User = new TableQuery(tag => new User(tag))

  /** Entity class storing rows of table Userdate
   *  @param id Database column ID SqlType(INT), AutoInc, PrimaryKey
   *  @param userid Database column UserID SqlType(INT)
   *  @param updatelast Database column UpdateLast SqlType(DATE), Default(None) */
  case class UserdateRow(id: Int, userid: Int, updatelast: Option[java.sql.Date] = None)
  /** GetResult implicit for fetching UserdateRow objects using plain SQL queries */
  implicit def GetResultUserdateRow(implicit e0: GR[Int], e1: GR[Option[java.sql.Date]]): GR[UserdateRow] = GR{
    prs => import prs._
    UserdateRow.tupled((<<[Int], <<[Int], <<?[java.sql.Date]))
  }
  /** Table description of table UserDate. Objects of this class serve as prototypes for rows in queries. */
  class Userdate(_tableTag: Tag) extends profile.api.Table[UserdateRow](_tableTag, Some("virtualpet"), "UserDate") {
    def * = (id, userid, updatelast) <> (UserdateRow.tupled, UserdateRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(userid), updatelast)).shaped.<>({r=>import r._; _1.map(_=> UserdateRow.tupled((_1.get, _2.get, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column UserID SqlType(INT) */
    val userid: Rep[Int] = column[Int]("UserID")
    /** Database column UpdateLast SqlType(DATE), Default(None) */
    val updatelast: Rep[Option[java.sql.Date]] = column[Option[java.sql.Date]]("UpdateLast", O.Default(None))
  }
  /** Collection-like TableQuery object for table Userdate */
  lazy val Userdate = new TableQuery(tag => new Userdate(tag))

  /** Entity class storing rows of table Userevent
   *  @param id Database column ID SqlType(INT), AutoInc, PrimaryKey
   *  @param userid Database column UserID SqlType(INT)
   *  @param notificationid Database column NotificationID SqlType(INT)
   *  @param senddate Database column SendDate SqlType(DATE)
   *  @param viewed Database column Viewed SqlType(ENUM) */
  case class UsereventRow(id: Int, userid: Int, notificationid: Int, senddate: java.sql.Date, viewed: Char)
  /** GetResult implicit for fetching UsereventRow objects using plain SQL queries */
  implicit def GetResultUsereventRow(implicit e0: GR[Int], e1: GR[java.sql.Date], e2: GR[Char]): GR[UsereventRow] = GR{
    prs => import prs._
    UsereventRow.tupled((<<[Int], <<[Int], <<[Int], <<[java.sql.Date], <<[Char]))
  }
  /** Table description of table UserEvent. Objects of this class serve as prototypes for rows in queries. */
  class Userevent(_tableTag: Tag) extends profile.api.Table[UsereventRow](_tableTag, Some("virtualpet"), "UserEvent") {
    def * = (id, userid, notificationid, senddate, viewed) <> (UsereventRow.tupled, UsereventRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(userid), Rep.Some(notificationid), Rep.Some(senddate), Rep.Some(viewed))).shaped.<>({r=>import r._; _1.map(_=> UsereventRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column UserID SqlType(INT) */
    val userid: Rep[Int] = column[Int]("UserID")
    /** Database column NotificationID SqlType(INT) */
    val notificationid: Rep[Int] = column[Int]("NotificationID")
    /** Database column SendDate SqlType(DATE) */
    val senddate: Rep[java.sql.Date] = column[java.sql.Date]("SendDate")
    /** Database column Viewed SqlType(ENUM) */
    val viewed: Rep[Char] = column[Char]("Viewed")
  }
  /** Collection-like TableQuery object for table Userevent */
  lazy val Userevent = new TableQuery(tag => new Userevent(tag))
}
