package com.cjw.note.dao;

import cn.hutool.core.util.StrUtil;
import com.cjw.note.po.Note;
import com.cjw.note.vo.NoteVo;

import java.util.ArrayList;
import java.util.List;

public class NoteDao {
    /**
     * 添加Or修改
       返回受影响的行数
     * @param note
     * @return
     */
    public int addOrUpdate(Note note) {
        //定义sql语句
        String sql ="insert into tb_note(typeId,title,content,pubTime) values (?,?,?,null)";
        //设置参数
        List<Object> obj =new ArrayList<>();
        obj.add(note.getTypeId());
        obj.add(note.getTitle());
        obj.add(note.getContent());
        //调用BaseDao更新方法
        int row =BaseDao.executeUpdate(sql,obj);
        return  row;
    }

    /**
     * 查询当前用户的数量，返回总数量
     * @param userId
     * @param title
     * @return
     */
    public long findNoteCount(Integer userId, String title,String date,String TypeId) {
        //定义sql语句
       String sql = "SELECT count(1) FROM tb_note n INNER JOIN tb_note_type t on n.typeId = t.typeId  WHERE userId = ? ";
        //设置 参数
        List<Object> obj =new ArrayList<>();
        obj.add(userId);
        //判断条件查询的参数是否为空 则拼接sql语句并设置相关参数
        if (!StrUtil.isBlank(title)){
            //拼接sql语句
            sql += " and title like concat('%',?,'%')";
            //设置相关参数
            obj.add(title);
        }else  if (!StrUtil.isBlank(date)){
            //拼接sql语句
            sql += " and date_format(pubTime,'%Y年%m月') = ? ";
            //设置相关参数
            obj.add(date);
        }
        else  if (!StrUtil.isBlank(TypeId)){
            //拼接sql语句
            sql += " and n.typeId = ? ";
            //设置相关参数
            obj.add(TypeId);
        }

        //调用BadeDao查询方法
        long count = (long) BaseDao.findSingLeValue(sql,obj);
        return count;
    }

    /**
     * 分页查询列表 返回note集合
     * @param userId
     * @param index
     * @param pageSize
     * @param title
     * @return
     */
    public List<Note> findNoteListByPage(Integer userId, Integer index, Integer pageSize, String title,String date,String TypeId) {
        //定义sql语句
        String sql = "SELECT noteId,title,pubTime FROM tb_note n INNER JOIN  tb_note_type t on n.typeId = t.typeId WHERE userId = ?";
        //设置 参数
        List<Object> obj =new ArrayList<>();
        obj.add(userId);
        //判断条件查询的参数是否为空 则拼接sql语句并设置相关参数
        if (!StrUtil.isBlank(title)){
            //拼接sql语句
            sql += " and title like concat('%',?,'%')";
            //设置相关参数
            obj.add(title);
        }else if (!StrUtil.isBlank(date)){
            //拼接sql语句
            sql += " and date_format(pubTime,'%Y年%m月') = ? ";
            //设置相关参数
            obj.add(date);
        }else  if (!StrUtil.isBlank(TypeId)){
            //拼接sql语句
            sql += " and n.typeId = ? ";
            //设置相关参数
            obj.add(TypeId);
        }
        //拼接分页的sql语句
        sql += " limit ?,?";
        obj.add(index);
        obj.add(pageSize);

        //调用BadeDao查询方法
        List<Note> list=BaseDao.queryRows(sql,obj,Note.class);
        return list;
    }

    /**
     * 通过日期分组查询当前登录用户下的数量
     * @param userId
     * @return
     */
    public List<NoteVo> findNoteCountByDate(Integer userId) {
        //定义sql语句
        String sql ="SELECT count( 1 ) noteCount, DATE_FORMAT( pubTime, '%Y年%m月' ) groupName FROM tb_note a LEFT JOIN tb_note_type b ON a.typeId = b.typeId WHERE userId = ? " +
                "GROUP BY DATE_FORMAT( pubTime, '%Y年%m月' ) " +
                "ORDER BY DATE_FORMAT( pubTime, '%Y年%m月' ) DESC";
        //设置 参数
        List<Object> obj =new ArrayList<>();
        obj.add(userId);
        List<NoteVo> list =BaseDao.queryRows(sql,obj,NoteVo.class);
        return list;
    }

    /**
     * 通过类型分组查询当前登录用户下的数量
     * @param userId
     * @return
     */
    public List<NoteVo> findNoteCountByType(Integer userId) {
        //定义sql语句
        String sql = "SELECT count(noteId) noteCount, t.typeId, typeName groupName FROM tb_note n " +
                " RIGHT JOIN tb_note_type t ON n.typeId = t.typeId WHERE userId = ? " +
                " GROUP BY t.typeId ORDER BY COUNT(noteId) DESC ";
        //设置 参数
        List<Object> obj =new ArrayList<>();
        obj.add(userId);
        List<NoteVo> list =BaseDao.queryRows(sql,obj,NoteVo.class);
        return list;
    }

    /**
     * 通过id查询对象
     * @param noteId
     * @return
     */
    public Note findNoteById(String noteId) {
        //定义sql语句
        String sql ="select noteId,title,content,pubTime,typeName from tb_note n inner join tb_note_type t on n.typeId=t.typeId where noteId=?";
        //设置 参数
        List<Object> obj =new ArrayList<>();
        obj.add(noteId);
        Note note= (Note) BaseDao.queryRow(sql,obj,Note.class);
        return note;
    }
}
