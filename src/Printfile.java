import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

class Printfile
{

    public static void main(String[] args)
    {

        String rootDir = "C:\\Users\\YIJIA\\Desktop\\Algorithm 2\\src\\input\\";


        Queue<File> queue = new LinkedList<>();


        queue.add(new File(rootDir));


        while (!queue.isEmpty())
        {

            File current = queue.poll();


            File[] listOfFilesAndDirectory = current.listFiles();


            if (listOfFilesAndDirectory != null)
            {


                for (File file : listOfFilesAndDirectory)
                {

                    if (file.isDirectory()) {
                        queue.add(file);
                    }

                    else {
                        System.out.println(file);
                    }
                }
            }
        }
    }
}
