import org.scalatest.FunSpec

class RegexSpec extends FunSpec {
  describe("regex") {
    val regex = """!(\[[^\[\]]*\])\([^\(\)]*\)""".r

    case class TestData(input: String, expected: String)
    val testDataList = Seq(
        TestData("""![\"onload=\"alert(1)]()""", """![\"onload=\"alert(1)]()"""),
        TestData("""![\"onload=\"alert(1)](aaaaaaaaaaa)""", """![\"onload=\"alert(1)](aaaaaaaaaaa)"""),
        TestData("""![\"onload=\"alert(1)](aaaaaaaaaaa) \n - [ ] test""", """![\"onload=\"alert(1)](aaaaaaaaaaa)"""),
        TestData("""![\"onload=\"alert(1)](aaaaaaaaaaa) \n - [ ] test, ![][] !""", """![\"onload=\"alert(1)](aaaaaaaaaaa)"""),
        TestData("""![\"onload=\"alert(1)](aaaaaaaaaaa) \n - [ ] test, ![][] ! ![\"onload=\"alert(1)](bbbb)""", """![\"onload=\"alert(1)](aaaaaaaaaaa),![\"onload=\"alert(1)](bbbb)"""),
        TestData("""[![画像](/img/gazou.gif)](http://gazou.html)""", """![画像](/img/gazou.gif)""")
      )
    testDataList.zipWithIndex.map { case (data, index) =>
      it(s"画像を表示するMarkdown記法を取得する - ${index}") {
        val actual = regex.findAllIn(data.input).mkString(",")
        assert(data.expected == actual)
      }
    }
  }
}
