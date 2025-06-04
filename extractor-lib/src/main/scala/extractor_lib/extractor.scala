package extractor_lib

import scala.meta._
import scala.meta.inputs.Input.VirtualFile
import scala.collection.mutable.Builder
import Utils.prettyPrint
import cats.data.OptionT
import cats.syntax.all._
import core.AST.AstType.{Basic, IO, Union}
import core.AST._

class Extractor {

  def processReturnType(t: Type): AstType = {
    IO("Future", Union(Basic("Done"), Basic("ResultError")))
    t match{
      case Type.Apply.After_4_6_0(
          Type.Name(name),
          Type.ArgClause(
          List(Type.ApplyInfix(
          Type.Name(a),Type.Name("|"),Type.Name(c)
          )
          ))
        ) =>
      IO(name, Union(Basic(a), Basic(c)))

    }
  }

  def processServiceMethod(t: Decl.Def): Option[Method] =

    t match {
      case Decl.Def.After_4_7_3(
      _lmod,
      Term.Name(methodName),
      _paramsGroup, // get params group
      rtype,
      ) =>
        Some(Method(methodName, processReturnType(rtype)))
      case _ => None
    }

  def processEnumCase(t: Defn.EnumCase): Option[EnumCasePair] =
    t match {
      case Defn.EnumCase.After_4_6_0(
            _,
            Term.Name(caseEnumName),
            _,
            _,
            List(
              Init.After_4_6_0(
                Type.Name(_name),
                _,
                List(
                  Term.ArgClause(List(Lit.String(r)), _),
                ),
              ),
            ),
          ) =>
        Some(EnumCasePair(caseEnumName, r))
      case _ => None
    }


  def processServiceTrait(t: Defn.Trait): Option[SimpleService] =
    t match {
      case Defn.Trait.After_4_6_0(
      _listMod,
      Type.Name(traitName),
      _paramClause,
      _ctorPrimary,
      Template.After_4_9_9(
      _block,
      _init,
      Template.Body(
      _self,
      l,
      ),
      _list,
      ),
      )

      =>

        l.flatMap {
          case e: Decl.Def => processServiceMethod(e)
          case _                => None
        }.some.map{
          vls => SimpleService(traitName, vls)
        }
      case _ => None
    }


  def processEnum(t: Defn.Enum): Option[SimpleEnum] =
    t match {
      case Defn.Enum.After_4_6_0(
            _,
            Type.Name(enumName),
            _b,
            _c,
            Template.After_4_9_9(
              a,
              List(Init.After_4_6_0(Type.Name("Model"), _, _)),
              Template.Body(
                _,
                l,
              ),
              _,
            ),
          ) =>

        l.flatMap {
          case e: Defn.EnumCase => processEnumCase(e)
          case _                => None
        }.some.map{
          vls => SimpleEnum(enumName, vls)
        }
      case _ => None
    }

  def processEnumSrc(src: String): Option[SimpleEnum] = {
    implicit val scala3: Dialect = dialects.Scala3
    val t: Parsed[Stat] = src.parse[Stat]
    t.get match {
      case r: Defn.Enum => processEnum(r)
      case _            => None
    }
  }

  def processServiceTraitSrc(src: String): Option[SimpleService] = {
    implicit val scala3: Dialect = dialects.Scala3
    val t: Parsed[Stat] = src.parse[Stat]
    t.get match {
      case r: Defn.Trait => processServiceTrait(r)
      case _            => None
    }
  }

  def run(): List[ASTNode] = {
    var result: List[ASTNode] = List()
    val input = Utils.readVirtualFile("enum.scala.1")
    implicit val scala3: Dialect = dialects.Scala3
    val t: Parsed[Source] = input.parse[Source]

    t.get.stats(0) match {
      case Pkg.After_4_9_9(
            (
              Term.Name(name),
              Pkg.Body(l),
            ),
          ) =>
        val res = l.flatMap {
          case o: Defn.Object => processObject(o)
          case _              => None
        }


        result = res

      case _ => println("rest...")
    }
    result
  }

  def processObject(t: Defn.Object) =
    t match {
      case Defn.Object(
            _,
            name2,
            Template.After_4_9_9(
              _,
              _,
              Template.Body(
                _,
                l,
              ),
              _,
            ),
          ) =>

        l.flatMap {
          case r: Defn.Enum => processEnum(r)
          case _            => None
        }

      case _ => None
    }

}
