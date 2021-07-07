package com.cjw.note.dao;

import com.cjw.note.po.Note;

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
     * @return
     */
    public long findNoteCount(Integer userId) {
        //定义sql语句
        String sql ="SELECT count( 1 ) FROM tb_note tb INNER JOIN tb_note_type t ON tb.typeId = t.userId WHERE userId =?";
        //设置 参数
        List<Object> obj =new ArrayList<>();
        obj.add(userId);
        //调用BadeDao查询方法
        long count = (long) BaseDao.findSingLeValue(sql,obj);
        return count;
    }

    /**
     * 分页查询列表 返回note集合
     * @param userId
     * @param index
     * @param pageSize
     * @return
     */
    public List<Note> findNoteListByPage(Integer userId, Integer index, Integer pageSize) {
        //定义sql语句
        String sql="SELECT noteId,title,pubTime FROM tb_note tb INNER JOIN tb_note_type t ON tb.typeId = t.userId WHERE userId =?";
        //设置 参数
        List<Object> obj =new ArrayList<>();
        obj.add(userId);
        //调用BadeDao查询方法
        List<Note> list=BaseDao.queryRows(sql,obj,Note.class);
        return list;
    }
}
