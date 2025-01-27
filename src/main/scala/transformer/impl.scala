package transformer

import core.AST.*

object Impl:

   class EnumTransformer extends Interface.EnumTransformer:

      def simpleEnum(t: SimpleEnum): String =
         val r = t.values.zipWithIndex.map:
              case (v, i) => f"${v.str.toUpperCase()} = ${i};"
         val s = r.mkString("\n  ")
         f"""
        |enum ${t.name} {
        |  ${s}
        |}
        |""".stripMargin

   class SimpleServiceTransformer extends Interface.TraitServiceTransformer:

      def service(t: SimpleService): String =
        ""
