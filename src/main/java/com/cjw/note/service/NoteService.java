package com.cjw.note.service;

import cn.hutool.core.util.StrUtil;
import com.cjw.note.dao.NoteDao;
import com.cjw.note.po.Note;
import com.cjw.note.vo.ResultInfo;

public class NoteService {

    private NoteDao noteDao =new NoteDao();

    /**
     * 添加或修改
         1. 设置回显对象 Note对象
         2. 参数的非空判断
            如果为空，code=0，msg=xxx，result=note对象，返回resultInfo对象
             2. 调用Dao层，添加云记记录，返回受影响的行数
         3. 判断受影响的行数
             如果大于0，code=1
             如果不大于0，code=0，msg=xxx，result=note对象
         4. 返回resultInfo对象
     * @param typeId
     * @param title
     * @param content
     * @return
     */
    public ResultInfo<Note> addOrUpdate(String typeId, String title, String content) {
        ResultInfo<Note> resultInfo =new ResultInfo<>();

        // 1. 参数的非空判断
        if (StrUtil.isBlank(typeId)){
            resultInfo.setCode(0);
            resultInfo.setMsg("请选择类型");
            return resultInfo;
        }
        if (StrUtil.isBlank(title)){
            resultInfo.setCode(0);
            resultInfo.setMsg("标题不能为空");
            return resultInfo;
        }
        if (StrUtil.isBlank(content)){
            resultInfo.setCode(0);
            resultInfo.setMsg("内容不能为空");
            return resultInfo;
        }
        //2、设置回显对象
        Note note=new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setTypeId(Integer.parseInt(typeId));
        resultInfo.setResult(note);
        //3、 调用Dao层，添加云记记录，返回受影响的行数
        int row =noteDao.addOrUpdate(note);
        //4. 返回resultInfo对象
        if (row>0){
            resultInfo.setCode(1);

        }else {
            resultInfo.setCode(0);
            resultInfo.setResult(note);
            resultInfo.setMsg("更新失败!");
        }
        return resultInfo;
    }
}
