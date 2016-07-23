package Classes;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *  1. Сделать кнопку при нажатии на которую выведется
 список всех загруженных фотографий с их id.
    2. Сделать возможность выбрать из списка часть фото и
 удалить одним нажатием на кнопку.
 */
@Controller
@RequestMapping("/")
public class MyController {

    private Map<Long, byte[]> photos = new HashMap<Long, byte[]>();

    @RequestMapping("/")
    public String onIndex() {
        return "index";
    }

    @RequestMapping(value = "/add_photo", method = RequestMethod.POST)
    public String onAddPhoto(Model model, @RequestParam MultipartFile photo) {
        if (photo.isEmpty())
            throw new PhotoErrorException();
        try {
            long id = System.currentTimeMillis();
            photos.put(id, photo.getBytes());

            model.addAttribute("photo_id", id);
            return "result";
        } catch (IOException e) {
            throw new PhotoErrorException();
        }
    }

    @RequestMapping("/photo/{photo_id}")
    public ResponseEntity<byte[]> onPhoto(@PathVariable("photo_id") long id) {
        return photoById(id);
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ResponseEntity<byte[]> onView(@RequestParam("photo_id") long id) {
        return photoById(id);
    }

    @RequestMapping("/delete/{photo_id}")
    public String onDelete(@PathVariable("photo_id") long id) {
        if (photos.remove(id) == null)
            throw new PhotoNotFoundException();
        else
            return "index";
    }


    /*------- 2 Task. Method delete selected IDs -------*/
    @RequestMapping(value = "/delete_selected", method = RequestMethod.POST)
    public String onDeleteSelected(@RequestParam(value = "toDelete[]", required = false) long[] ids) {
        if (ids != null) {
            for (int i = 0; i < ids.length; i++) {
                long id = ids[i];
                photos.remove(id);
            }
        }
        return "index";
    }

    /*------- 1 Task. Method view all IDs -------*/
    @RequestMapping(value = "/view_all", method = RequestMethod.POST)
    public String onViewAll(Model model) {
        if (photos.isEmpty()) {
            throw new PhotoNotFoundException();
        } else {
            model.addAttribute("list_photos", photos);
            return "listPhotos";
        }
    }

    private ResponseEntity<byte[]> photoById(long id) {
        byte[] bytes = photos.get(id);
        if (bytes == null)
            throw new PhotoNotFoundException();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }
}
