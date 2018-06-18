package sh.ramen.service;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import sh.ramen.dto.Ramen;

@Service
public class FavyService {

    public List<Ramen> findAll() {
        List<Ramen> list = new ArrayList<Ramen>();
        Reader reader = null;
        try {
            reader = new XmlReader(new URL("http://www.favy.jp/index.rss"));
            SyndFeed feed = new SyndFeedInput().build(reader);
            for (Object obj : feed.getEntries()) {
            	Ramen Shop = toShop((SyndEntry) obj);
            	if (Shop != null) {
            		list.add(Shop);
            	}
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        return list;
    }

    private Ramen toShop(SyndEntry entry) {
    	String title = entry.getTitle();
    	if (!title.contains("ラーメン")) {
    		return null;
    	}
        Ramen shop = new Ramen();
        shop.setTitle(title);
        shop.setUrl(entry.getLink());
        shop.setDescription(entry.getDescription().getValue());
        shop.setPublishedDate(entry.getPublishedDate());
        return shop;
    }
}
