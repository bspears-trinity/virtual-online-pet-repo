package models

import javax.inject._
import scala.collection.mutable.ArrayBuffer
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.duration.Duration
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import Tables._
import com.mysql.cj.x.protobuf.MysqlxCrud.Limit

class PetDBModel {
  //def getList(username: String, db: Database)(implicit ec: ExecutionContext): Future[Seq[TasksRow]]
  def getLogin(username: String, password: String, db: Database)(implicit ec: ExecutionContext): Future[Boolean] {
    //TODO
  }
  
  def getInfo(username: String, db: Database)implicit ec: ExecutionContext): Future[UserRow] {
    //TODO
  }
  
  
  
}