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
  lazy val schema: profile.SchemaDescription = Event.schema ++ Pet.schema ++ Stats.schema ++ User.schema ++ Userevent.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Event
   *  @param id Database column ID SqlType(INT), AutoInc, PrimaryKey
   *  @param message Database column Message SqlType(TEXT), Default(None)
   *  @param probability Database column Probability SqlType(FLOAT)
   *  @param moneyinc Database column MoneyInc SqlType(INT)
   *  @param affectioninc Database column AffectionInc SqlType(INT)
   *  @param hungerinc Database column HungerInc SqlType(INT)
   *  @param exhaustioninc Database column ExhaustionInc SqlType(INT)
   *  @param eventaction Database column EventAction SqlType(TEXT), Default(None) */
  case class EventRow(id: Int, message: Option[String] = None, probability: Float, moneyinc: Int, affectioninc: Int, hungerinc: Int, exhaustioninc: Int, eventaction: Option[String] = None)
  /** GetResult implicit for fetching EventRow objects using plain SQL queries */
  implicit def GetResultEventRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Float]): GR[EventRow] = GR{
    prs => import prs._
    EventRow.tupled((<<[Int], <<?[String], <<[Float], <<[Int], <<[Int], <<[Int], <<[Int], <<?[String]))
  }
  /** Table description of table Event. Objects of this class serve as prototypes for rows in queries. */
  class Event(_tableTag: Tag) extends profile.api.Table[EventRow](_tableTag, Some("virtualpet"), "Event") {
    def * = (id, message, probability, moneyinc, affectioninc, hungerinc, exhaustioninc, eventaction) <> (EventRow.tupled, EventRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), message, Rep.Some(probability), Rep.Some(moneyinc), Rep.Some(affectioninc), Rep.Some(hungerinc), Rep.Some(exhaustioninc), eventaction)).shaped.<>({r=>import r._; _1.map(_=> EventRow.tupled((_1.get, _2, _3.get, _4.get, _5.get, _6.get, _7.get, _8)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column Message SqlType(TEXT), Default(None) */
    val message: Rep[Option[String]] = column[Option[String]]("Message", O.Default(None))
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
    /** Database column EventAction SqlType(TEXT), Default(None) */
    val eventaction: Rep[Option[String]] = column[Option[String]]("EventAction", O.Default(None))
  }
  /** Collection-like TableQuery object for table Event */
  lazy val Event = new TableQuery(tag => new Event(tag))

  /** Entity class storing rows of table Pet
   *  @param id Database column ID SqlType(INT), AutoInc, PrimaryKey
   *  @param userid Database column UserID SqlType(INT), Default(None)
   *  @param name Database column Name SqlType(VARCHAR), Length(25,true)
   *  @param adoptiondate Database column AdoptionDate SqlType(DATE)
   *  @param icon Database column Icon SqlType(LONGBLOB), Default(None) */
  case class PetRow(id: Int, userid: Option[Int] = None, name: String, adoptiondate: java.sql.Date, icon: Option[java.sql.Blob] = None)
  /** GetResult implicit for fetching PetRow objects using plain SQL queries */
  implicit def GetResultPetRow(implicit e0: GR[Int], e1: GR[Option[Int]], e2: GR[String], e3: GR[java.sql.Date], e4: GR[Option[java.sql.Blob]]): GR[PetRow] = GR{
    prs => import prs._
    PetRow.tupled((<<[Int], <<?[Int], <<[String], <<[java.sql.Date], <<?[java.sql.Blob]))
  }
  /** Table description of table Pet. Objects of this class serve as prototypes for rows in queries. */
  class Pet(_tableTag: Tag) extends profile.api.Table[PetRow](_tableTag, Some("virtualpet"), "Pet") {
    def * = (id, userid, name, adoptiondate, icon) <> (PetRow.tupled, PetRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), userid, Rep.Some(name), Rep.Some(adoptiondate), icon)).shaped.<>({r=>import r._; _1.map(_=> PetRow.tupled((_1.get, _2, _3.get, _4.get, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column UserID SqlType(INT), Default(None) */
    val userid: Rep[Option[Int]] = column[Option[Int]]("UserID", O.Default(None))
    /** Database column Name SqlType(VARCHAR), Length(25,true) */
    val name: Rep[String] = column[String]("Name", O.Length(25,varying=true))
    /** Database column AdoptionDate SqlType(DATE) */
    val adoptiondate: Rep[java.sql.Date] = column[java.sql.Date]("AdoptionDate")
    /** Database column Icon SqlType(LONGBLOB), Default(None) */
    val icon: Rep[Option[java.sql.Blob]] = column[Option[java.sql.Blob]]("Icon", O.Default(None))
  }
  /** Collection-like TableQuery object for table Pet */
  lazy val Pet = new TableQuery(tag => new Pet(tag))

  /** Entity class storing rows of table Stats
   *  @param id Database column ID SqlType(INT), AutoInc, PrimaryKey
   *  @param petid Database column PetID SqlType(INT)
   *  @param affection Database column Affection SqlType(INT)
   *  @param hunger Database column Hunger SqlType(INT)
   *  @param exhaustion Database column Exhaustion SqlType(INT)
   *  @param dateofstats Database column DateOfStats SqlType(DATE) */
  case class StatsRow(id: Int, petid: Int, affection: Int, hunger: Int, exhaustion: Int, dateofstats: java.sql.Date)
  /** GetResult implicit for fetching StatsRow objects using plain SQL queries */
  implicit def GetResultStatsRow(implicit e0: GR[Int], e1: GR[java.sql.Date]): GR[StatsRow] = GR{
    prs => import prs._
    StatsRow.tupled((<<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[java.sql.Date]))
  }
  /** Table description of table Stats. Objects of this class serve as prototypes for rows in queries. */
  class Stats(_tableTag: Tag) extends profile.api.Table[StatsRow](_tableTag, Some("virtualpet"), "Stats") {
    def * = (id, petid, affection, hunger, exhaustion, dateofstats) <> (StatsRow.tupled, StatsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(petid), Rep.Some(affection), Rep.Some(hunger), Rep.Some(exhaustion), Rep.Some(dateofstats))).shaped.<>({r=>import r._; _1.map(_=> StatsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

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
    /** Database column DateOfStats SqlType(DATE) */
    val dateofstats: Rep[java.sql.Date] = column[java.sql.Date]("DateOfStats")
  }
  /** Collection-like TableQuery object for table Stats */
  lazy val Stats = new TableQuery(tag => new Stats(tag))

  /** Entity class storing rows of table User
   *  @param id Database column ID SqlType(INT), AutoInc, PrimaryKey
   *  @param username Database column Username SqlType(VARCHAR), Length(50,true)
   *  @param password Database column Password SqlType(VARCHAR), Length(50,true)
   *  @param money Database column Money SqlType(INT)
   *  @param creationdate Database column CreationDate SqlType(DATE)
   *  @param lastvisit Database column LastVisit SqlType(DATE) */
  case class UserRow(id: Int, username: String, password: String, money: Int, creationdate: java.sql.Date, lastvisit: java.sql.Date)
  /** GetResult implicit for fetching UserRow objects using plain SQL queries */
  implicit def GetResultUserRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Date]): GR[UserRow] = GR{
    prs => import prs._
    UserRow.tupled((<<[Int], <<[String], <<[String], <<[Int], <<[java.sql.Date], <<[java.sql.Date]))
  }
  /** Table description of table User. Objects of this class serve as prototypes for rows in queries. */
  class User(_tableTag: Tag) extends profile.api.Table[UserRow](_tableTag, Some("virtualpet"), "User") {
    def * = (id, username, password, money, creationdate, lastvisit) <> (UserRow.tupled, UserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(username), Rep.Some(password), Rep.Some(money), Rep.Some(creationdate), Rep.Some(lastvisit))).shaped.<>({r=>import r._; _1.map(_=> UserRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column Username SqlType(VARCHAR), Length(50,true) */
    val username: Rep[String] = column[String]("Username", O.Length(50,varying=true))
    /** Database column Password SqlType(VARCHAR), Length(50,true) */
    val password: Rep[String] = column[String]("Password", O.Length(50,varying=true))
    /** Database column Money SqlType(INT) */
    val money: Rep[Int] = column[Int]("Money")
    /** Database column CreationDate SqlType(DATE) */
    val creationdate: Rep[java.sql.Date] = column[java.sql.Date]("CreationDate")
    /** Database column LastVisit SqlType(DATE) */
    val lastvisit: Rep[java.sql.Date] = column[java.sql.Date]("LastVisit")
  }
  /** Collection-like TableQuery object for table User */
  lazy val User = new TableQuery(tag => new User(tag))

  /** Entity class storing rows of table Userevent
   *  @param id Database column ID SqlType(INT), AutoInc, PrimaryKey
   *  @param userid Database column UserID SqlType(INT)
   *  @param notificationid Database column NotificationID SqlType(INT)
   *  @param senddate Database column SendDate SqlType(DATE) */
  case class UsereventRow(id: Int, userid: Int, notificationid: Int, senddate: java.sql.Date)
  /** GetResult implicit for fetching UsereventRow objects using plain SQL queries */
  implicit def GetResultUsereventRow(implicit e0: GR[Int], e1: GR[java.sql.Date]): GR[UsereventRow] = GR{
    prs => import prs._
    UsereventRow.tupled((<<[Int], <<[Int], <<[Int], <<[java.sql.Date]))
  }
  /** Table description of table UserEvent. Objects of this class serve as prototypes for rows in queries. */
  class Userevent(_tableTag: Tag) extends profile.api.Table[UsereventRow](_tableTag, Some("virtualpet"), "UserEvent") {
    def * = (id, userid, notificationid, senddate) <> (UsereventRow.tupled, UsereventRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(userid), Rep.Some(notificationid), Rep.Some(senddate))).shaped.<>({r=>import r._; _1.map(_=> UsereventRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column UserID SqlType(INT) */
    val userid: Rep[Int] = column[Int]("UserID")
    /** Database column NotificationID SqlType(INT) */
    val notificationid: Rep[Int] = column[Int]("NotificationID")
    /** Database column SendDate SqlType(DATE) */
    val senddate: Rep[java.sql.Date] = column[java.sql.Date]("SendDate")
  }
  /** Collection-like TableQuery object for table Userevent */
  lazy val Userevent = new TableQuery(tag => new Userevent(tag))
}
