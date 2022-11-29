package com.example.cloudstorageclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.buffer.ByteBuf;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Message {
    public String file= "null";
    public String filename="null";
    public String login="null";
    public String password="null";
    public MsgType typ=MsgType.Registration;
    public String files="";
    public enum MsgType{
        FileUpload, FileRequest, Registration, FileList, FileUpdate, FileDelete, Login, LoginSuccess
    }
    public void initFromByteBuf(ByteBuf buf){
        Gson g = new GsonBuilder().setLenient().create();
        String b = "";
        String c= "";
        byte bute[]= {2};
        for (int i = 0; i < buf.capacity(); i++) {
            bute[0] = buf.getByte(i);


            b=b+ new String(bute);
        }
        for (int i = 0; i <= b.indexOf("}"); i++) {
            bute[0] = b.getBytes(StandardCharsets.UTF_8)[i];

            c=c+ new String(bute);
        }



        Message m = g.fromJson(c, Message.class);
        file = m.file;
        filename = m.filename;
        login = m.login;
        password = m.password;
        typ = m.typ;
        files = m.files;
    }

    public MsgType getMsgType(){
        return this.typ;
    }

    public ByteBuf fillByteBuff(ByteBuf b){
        Gson g = new GsonBuilder().create();
        ByteBuf bs = b;
        bs.writeBytes(g.toJson(this).getBytes(StandardCharsets.UTF_8));
        return b;


    }
}
