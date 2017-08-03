import sbt._
import sbt.Keys._

lazy val buildSettings = Seq(
  organization := "com.meetup",
  scalaVersion := "2.12.3",
  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
  homepage := Some(url("http://github.com/meetup/archery")),
  version := "0.5.0",
  crossScalaVersions := Seq("2.10.5", "2.11.6", "2.12.3"))

lazy val commonSettings = Seq(
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:experimental.macros",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint",
    //"-Yno-adapted-args",
    "-Ywarn-dead-code",
    //"-Ywarn-numeric-widen",
    //"-Ywarn-value-discard",
    "-Xfuture"))

lazy val publishSettings = Seq(
  bintrayOrganization := Some("meetup"))

lazy val noPublishSettings = Seq(
  publish := (),
  publishLocal := (),
  publishArtifact := false)

lazy val archerySettings =
  buildSettings ++ commonSettings ++ publishSettings

lazy val archery =
  project.in(file("."))
  .settings(moduleName := "aggregate")
  .settings(archerySettings)
  .settings(noPublishSettings)
  .aggregate(core, benchmark)

lazy val core =
  project
  .settings(moduleName := "archery")
  .settings(archerySettings)
  .settings(libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.scalacheck" %% "scalacheck" % "1.13.5" % "test"))

lazy val benchmark =
  project.dependsOn(core)
  .settings(moduleName := "archery-benchmark")
  .settings(archerySettings)
  .settings(noPublishSettings)
  .settings(Seq(
    fork in run := true,
    javaOptions in run += "-Xmx4G",
    libraryDependencies ++= Seq(
    "com.github.ichoran" %% "thyme" % "0.1.2-SNAPSHOT"),
    resolvers ++= Seq(("Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"), Resolver.sonatypeRepo("releases"))))
