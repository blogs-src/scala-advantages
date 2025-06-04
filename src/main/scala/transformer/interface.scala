package transformer

import core.AST.*

object Interface:

   trait EnumTransformer:

      def simpleEnum(
        t: SimpleEnum,
      ): String

   trait TraitServiceTransformer:

      def service(
        t: SimpleService,
      ): String
