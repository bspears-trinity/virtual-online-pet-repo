package models

import javax.inject._
import scala.collection.mutable.ArrayBuffer
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.duration.Duration
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import Tables._

class PetDBModel {
  //def getList(username: String, db: Database)(implicit ec: ExecutionContext): Future[Seq[TasksRow]]
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
      val sqlDate = new java.sql.Date(date.getTime())
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
      val sqlDate = new java.sql.Date(date.getTime())
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
      val sqlDate = new java.sql.Date(date.getTime())
      db.run(Pet += PetRow(0, seq.head, petName, sqlDate, Icon))
    }
  }
  
  def removePet(username: String, petName: String, Icon: Int, db: Database)(implicit ec: ExecutionContext): Future[Int] = {
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
      val sqlDate = new java.sql.Date(date.getTime())
      val prev = db.run { Stats.filter(_.petid === seq.head).sortBy(_.statdate.desc).result }
      prev.flatMap { p =>
        db.run(Stats += StatsRow(0, p.head.id, p.head.affection+AffectionInc,p.head.hunger+HungerInc,
                                    p.head.exhaustion+ExhaustionInc, sqlDate))
      }
    }
  }
  
  def addEvent(username: String, message: String, Probability: Float, AffectionInc: Int, HungerInc: Int, ExhaustionInc: Int, MoneyInc: Int, db: Database)(implicit ec: ExecutionContext): Future[Int] = {
  	//add event to table and apply to user. If event is already found, only apply to user
  	val k = db.run {
  	  Event.filter(_.message === message).exists.result
  	}
  	
  	k.map(b => if(!b) { 
  	  db.run(Event += EventRow(0,message,Probability,AffectionInc,HungerInc,ExhaustionInc,MoneyInc))
  	})
  	
    val uids = db.run {
      (for {
        u <- User
        if u.username === username
      } yield {
        u.id
      }).result
    }
  	
  	val eids = db.run {
      (for {
        e <- Event
        if e.message === message
      } yield {
        e.id
      }).result
    }
  	
    uids.flatMap { useq =>
      eids.flatMap { eseq =>
        val date = new java.util.Date()
        val sqlDate = new java.sql.Date(date.getTime())
        db.run(Userevent += UsereventRow(0, useq.head, eseq.head, sqlDate, 'F'))        
      }
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
  	  db.run(Stats.filter(_.petid === seq.head).sortBy(_.statdate.desc).result.head)
  	}
  }
  
  def getNotif(username: String, db: Database)(implicit ec: ExecutionContext): Future[Seq[EventRow]] = {
  	db.run {
  	  val allEvents = for {
  	    u <- User
  	    if u.username === username
  	    ue <- Userevent
  	    if ue.userid === u.id
  	    e <- Event
  	    if ue.notificationid === e.id
  	  } yield {
  	    e
  	  }
  	  allEvents.result
  	}
  }
  
  def getLastVist(username: String, db: Database)(implicit ec: ExecutionContext): Future[UserdateRow] = {
  	db.run {
      (for {
        u <- User
        if u.username === username
        ud <- Userdate
        if ud.userid === u.id
      } yield {
        ud
      }).sortBy(_.updatelast.desc).result.head
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
      }).sortBy(_.adoptiondate.desc).result.head
    }
  }
  
  
}