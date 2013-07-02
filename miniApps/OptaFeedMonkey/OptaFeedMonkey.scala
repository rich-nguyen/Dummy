import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.Calendar

object OptaFeedMonkey
{

private def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit)
{
   val p = new PrintWriter(f)
   try
   {
      op(p)
   }
   finally
   {
     p.close()
   }
}

// Takes a single argument, a URL which will be downloaded to desktop
// in separate files every 5 minutes.
def main(args: Array[String])
{
    println("OptaFeedMonkey")

    while(true)
    {
      try
      {
        while(true)
        {
          val URL = args(0)
          val data = io.Source.fromURL(URL)

          val homeDir = System.getProperty("user.home");

          val simpleTime = new SimpleDateFormat("dd-MM-yyyy-HHmmss")
          val newFileName = simpleTime.format(Calendar.getInstance().getTime())

          val dir = new java.io.File(homeDir + "/Desktop/cricketfeed/")
          dir.mkdir();

          println("making new file at " + homeDir + "/Desktop/cricketfeed/" + newFileName)
          printToFile(new java.io.File(homeDir, "/Desktop/cricketfeed/"+ newFileName + ".txt"))(p =>
          {
            for ( line <- data.getLines())
              {
                p.println(line)
              }
          })

          //5 mins
          Thread.sleep(300000)
        }
      }
      catch
      {
        case _ => println("an error")
      }

      Thread.sleep(10000)
    }
}

} // OptaFeedMonkey

