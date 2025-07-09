//package KritimBackend.KritimBackend.service;
//
//import KritimBackend.KritimBackend.dtos.NoticeDtos;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//public interface NoticeService {
//
//    NoticeDtos addNotice(NoticeDtos dto, MultipartFile file, Long userId) throws IOException;
//
//
//    NoticeDtos getNoticeById(Integer id);
//
//    List<NoticeDtos> getAll();
//
//    NoticeDtos updateNotice(NoticeDtos dto, MultipartFile file, Long userId) throws IOException;
//
//    boolean deleteNotice(Integer id, Long userId);
//
//};