package tse.fi2.info4.tbek;

import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Download {
	
	public static void downloadWebpage(String url, String title)
	{
		new File(title).mkdirs(); //cree le repertoire de stockage
		
		try {
			
			getHtml(url, title); // ercupere le fichier html de la page
			
            //recupere le html  pour le parser
            Document doc = Jsoup.connect(url).get();

            //Recupere la liste toutes les images
            Elements img = doc.getElementsByTag("img");

            for (Element el_img : img) {

                //pour chaque image, on recuprere son adresse absolue
                String src = el_img.absUrl("src");

                System.out.println("Image Found!");
                System.out.println("src attribute is : "+src);
				
				// on telecharge l'image
                getElement(src, title);

            }
            
            //Recupere la liste de toutes les css
            Elements css = doc.getElementsByTag("link");

            for (Element el_css : css) {

                //pour chaque css, recupere son adresse absolue
                String src = el_css.absUrl("href");

                System.out.println("Css Found!");
                System.out.println("src attribute is : "+src);
				
				//telecharge le css
                getElement(src, title);

            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
		
	}
		

	private static void getHtml(String html_url, String folderName) throws IOException
	{	
		// recupere le fichier html dans un input stream, et le copie dans un FileoutputStream
		URL url = new URL(html_url);
		InputStream in = new BufferedInputStream(url.openStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		byte[] buf = new byte[1024]; //buffer pour la copie de l'input Stream
		int n = 0;
		while (-1 != (n=in.read(buf)))
		{
			out.write(buf, 0, n);
		}
		out.close();
		in.close();
		
		byte[] response = out.toByteArray();
		
		FileOutputStream fos = new FileOutputStream(folderName + "/" + "index.html");
		fos.write(response);
		fos.close();
	}

    private static void getElement(String src, String folderName) throws IOException {

     

        //recupere le nom de l'element, c'est-a-dire la derniere partie de l'url, entre le dernier / et la fin de l'url
        int indexname = src.lastIndexOf("/");

        if (indexname == src.length()) {
            src = src.substring(1, indexname);
        }

        indexname = src.lastIndexOf("/");
        String name = src.substring(indexname, src.length());

        System.out.println(name);

        //ouvre l'element dans un inputStream
        URL url = new URL(src);
        InputStream in = url.openStream();

        OutputStream out = new BufferedOutputStream(new FileOutputStream( folderName + "/" + name));

        for (int b; (b = in.read()) != -1;) {
            out.write(b);
        }
        out.close();
        in.close();

    }
    
    public static void main(String[] args) {

	downloadWebpage("http://raysohn.com/posts/decoding-floats.html", "float");
        
    }
}