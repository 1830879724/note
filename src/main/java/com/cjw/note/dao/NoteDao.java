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
}
