package models

import javax.inject._
import scala.collection.mutable.ArrayBuffer
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.duration.Duration
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import Tables._

@Singleton
object PetDBModel {
  
  def getLogin(username: String, password: String, db: Database)(implicit ec: ExecutionContext): Future[Boolean] = {
    db.run {
      User.filter(u => u.username === username && u.password === password).exists.result
    }
  }
  
  def findUser(username: String, db: Database)(implicit ec: ExecutionContext): Future[Boolean] = {
    db.run {
      User.filter(u => u.username === username).exists.result
    }
  }
  
  def getInfo(username: String, db: Database)(implicit ec: ExecutionContext): Future[UserRow] = {
    db.run {
      User.filter(_.username === username).result.head
    }
  }
  
  def addUser(username: String, password: String, db: Database)(implicit ec: ExecutionContext): Future[Int] = {
    db.run {
      val date = new java.util.Date()
      val sqlDate = new java.sql.Timestamp(date.getTime)
      User += UserRow(0, username, password, sqlDate)
    }
  }
  
  def addVisit(username: String, db: Database)(implicit ec: ExecutionContext): Future[Int] = {
	  
	  val ids = db.run {
      (for {
        u <- User
        if u.username === username
      } yield {
        u.id
      }).result
    }
    ids.flatMap { seq => 
      val date = new java.util.Date()
      val sqlDate = new java.sql.Timestamp(date.getTime)
      db.run(Userdate += UserdateRow(0,seq.head,Option(sqlDate)))
    }
  }
  
  def changePass(username: String, oldPass: String, newPass: String, db: Database)(implicit ec: ExecutionContext): Future[Int] = {
  	db.run {
  	  val user = for {
  	    u <- User
  	    if u.username === username
  	    if u.password === oldPass
  	  } yield {
  	    u.password
  	  }
  	  user.update(newPass)
  	}
  }
  
  def addPet(username: String, petName: String, Icon: Int, db: Database)(implicit ec: ExecutionContext): Future[Int] = {
  	val ids = db.run {
      (for {
        u <- User
        if u.username === username
      } yield {
        u.id
      }).result
    }
    ids.flatMap { seq => 
      val date = new java.util.Date()
      val sqlDate = new java.sql.Timestamp(date.getTime)
      db.run(Pet += PetRow(0, seq.head, petName, sqlDate, Icon))
    }
  }
  
  def removePet(username: String, db: Database)(implicit ec: ExecutionContext): Future[Int] = {
  	val ids = db.run {
      (for {
        u <- User
        if u.username === username
      } yield {
        u.id
      }).result
    }
    ids.flatMap { seq =>
      db.run {
        Pet.filter(t => t.userid === seq.headOption).delete
      }
    }
  }
  
  def updateStats(username: String, AffectionInc: Int, HungerInc: Int, ExhaustionInc: Int, db: Database)(implicit ec: ExecutionContext): Future[Int] = {
  	val ids = db.run {
      (for {
        u <- User
        if u.username === username
        p <- Pet
        if p.userid === u.id
      } yield {
        p.id
      }).result
    }
    ids.flatMap { seq =>
      val date = new java.util.Date()
      val sqlDate = new java.sql.Timestamp(date.getTime)
      val prev = db.run { Stats.filter(_.petid === seq.head).sortBy(_.statdate.desc).take(1).result }
      prev.flatMap { p =>
        var aff = p.head.affection+AffectionInc
        if(aff < 0) aff = 0
        if(aff > 100) aff = 100
        var hung = p.head.hunger+HungerInc
        if(hung < 0) hung = 0
        if(hung > 100) hung = 100
        var exh = p.head.exhaustion+ExhaustionInc
        if(exh < 0) exh = 0
        if(exh > 100) exh = 100
        db.run(Stats += StatsRow(0, seq.head, aff,hung, exh, sqlDate))
      }
    }
  }
  
  def newStats(username: String, db: Database)(implicit ec: ExecutionContext): Future[Int] = {
  	val ids = db.run {
      (for {
        u <- User
        if u.username === username
        p <- Pet
        if p.userid === u.id
      } yield {
        p.id
      }).result
    }
    ids.flatMap { seq =>
      val date = new java.util.Date()
      val sqlDate = new java.sql.Timestamp(date.getTime)
      db.run(Stats += StatsRow(0, seq.head, 50, 0, 0, sqlDate))
    }
  }
  
  def updateMoney(username: String, MoneyInc: Int, db: Database)(implicit ec: ExecutionContext): Future[Int] = {
  	val ids = db.run {
      (for {
        u <- User
        if u.username === username
      } yield {
        u.id
      }).result
    }
    ids.flatMap { seq =>
      val date = new java.util.Date()
      val sqlDate = new java.sql.Timestamp(date.getTime)
      val prev = db.run { Money.filter(_.userid === seq.head).sortBy(_.dateofmoney.desc).take(1).result.head }
      prev.flatMap { p =>
        db.run(Money += MoneyRow(0, seq.head, p.money + MoneyInc, sqlDate))
      }
    }
  }
  
  def newMoney(username: String, db: Database)(implicit ec: ExecutionContext): Future[Int] = {
    val ids = db.run {
      (for {
        u <- User
        if u.username === username
      } yield {
        u.id
      }).result
    }
    ids.flatMap { seq =>
      val date = new java.util.Date()
      val sqlDate = new java.sql.Timestamp(date.getTime)
      db.run(Money += MoneyRow(0, seq.head, 1000, sqlDate))
    }
  }
  
  def addEvent(username: String, EventID: Int, db: Database)(implicit ec: ExecutionContext): Future[Int] = {
  	//add event to table and apply to user. If event is already found, only apply to user
    val uids = db.run {
      (for {
        u <- User
        if u.username === username
      } yield {
        u.id
      }).result
    }
  	
    uids.flatMap { useq =>
      val date = new java.util.Date()
      val sqlDate = new java.sql.Timestamp(date.getTime)
      db.run(Userevent += UsereventRow(0, useq.head, EventID, sqlDate, 'F'))        
    }
  }
  
  def viewEvent(username: String, message: String, db: Database)(implicit ec: ExecutionContext): Future[Int] =  {
    db.run {
      val view =for {
        u <- User
        if u.username === username
        e <- Event
        if e.message === message
        ue <- Userevent
        if e.id === ue.notificationid
        if u.id === ue.userid
      } yield {
        ue.viewed
      }
      view.update('T')
    }
  }
  
  def getStats(username: String, db: Database)(implicit ec: ExecutionContext): Future[StatsRow] = {
  	val ids = db.run {
      (for {
        u <- User
        if u.username === username
        p <- Pet
        if p.userid === u.id
      } yield {
        p.id
      }).result
    }
  	ids.flatMap { seq =>
  	  db.run(Stats.filter(_.petid === seq.head).sortBy(_.statdate.desc).take(1).result.head)
  	}
  }
  
  def getMoney(username: String, db: Database)(implicit ec: ExecutionContext): Future[MoneyRow] = {
    val ids = db.run {
      (for {
        u <- User
        if u.username === username
      } yield {
        u.id
      }).result
    }
    ids.flatMap { seq =>
      db.run(Money.filter(_.userid === seq.head).sortBy(_.dateofmoney.desc).take(1).result.head)
    }
  }
  
  def getEvent(eventID: Int, db: Database)(implicit ec: ExecutionContext): Future[EventRow] = {
    db.run(Event.filter(_.id === eventID).result.head)
  }
  
  def getNotif(username: String, db: Database)(implicit ec: ExecutionContext): Future[Seq[EventRow]] = {
  	db.run {
  	  val allEvents = for {
  	    u <- User
  	    if u.username === username
  	    ue <- Userevent
  	    if ue.userid === u.id
  	    if ue.viewed === 'F'
  	    e <- Event
  	    if ue.notificationid === e.id
  	  } yield {
  	    e
  	  }
  	  allEvents.result
  	}
  }
  
  def getEvents(username: String, db: Database)(implicit ec: ExecutionContext): Future[Seq[(EventRow,UsereventRow)]] = {
  	db.run {
  	  val allEvents = for {
  	    u <- User
  	    if u.username === username
  	    ue <- Userevent
  	    if ue.userid === u.id
  	    e <- Event
  	    if ue.notificationid === e.id
  	  } yield {
  	    (e,ue)
  	  }
  	  allEvents.result
  	}
  }
  
  def getLastVisit(username: String, db: Database)(implicit ec: ExecutionContext): Future[UserdateRow] = {
  	db.run {
      (for {
        u <- User
        if u.username === username
        ud <- Userdate
        if ud.userid === u.id
      } yield {
        ud
      }).sortBy(_.updatelast.desc).take(1).result.head
    }
  }
  
  def getPet(username: String, db: Database)(implicit ec: ExecutionContext): Future[PetRow] = {
  	db.run {
      (for {
        u <- User
        if u.username === username
        p <- Pet
        if p.userid === u.id
      } yield {
        p
      }).sortBy(_.adoptiondate.desc).take(1).result.head
    }
  }
  
  
}