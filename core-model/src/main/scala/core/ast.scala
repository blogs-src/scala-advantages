package core

object AST:

   enum AstType:

      case Option(
        name: AstType)

      case List(
        name: AstType)

      case IO(
        name: String,
        params: AstType,
             )

      case Union(
        a: AstType,
        b: AstType)

      case Basic(
        name: String)

   case class EnumCasePair(
     value: String,
     str:   String)

   case class Method(
     name:    String,
     resType: AstType)

   trait ASTNode

   case class SimpleEnum(
     name: String,
     values: List[EnumCasePair]) extends ASTNode

   case class SimpleService(
     name: String,
     methods: List[Method]) extends ASTNode
