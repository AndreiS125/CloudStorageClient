package com.example.cloudstorageclient;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ByteProcessor;
import io.netty.util.ReferenceCountUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

import static com.example.cloudstorageclient.HelloApplication.*;

public class Network {
    public final int port = 8080;
    public boolean started = false;
    public Channel chanell;

    public String files;
    public ByteBuf c = (ByteBuf) new ByteBufff();


    public void Network() throws Exception {

        EventLoopGroup group = new NioEventLoopGroup(1); // (1)
        System.out.println("Starting Network...");
        try {
            Bootstrap b = new Bootstrap(); // (2)
            b.group(group)
                    .channel(NioSocketChannel.class) // (3)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws IOException {     //(2)
                                    ByteBuf in = (ByteBuf) msg;
                                    System.out.println("Message read");


                                    Message message = new Message();
                                    message.initFromByteBuf((ByteBuf) msg);
                                    System.out.println(message.typ);
                                    if(message.typ== Message.MsgType.FileUpload){
                                        System.out.println("File upload from the server");
                                        File f = new File("saves/"+message.login+"/"+message.filename);
                                        if(f.exists()){
                                            f.createNewFile();
                                            Files.write(f.toPath(), message.file.getBytes(StandardCharsets.UTF_8));

                                        }
                                    }
                                    if(message.typ== Message.MsgType.LoginSuccess || message.typ== Message.MsgType.Login){
                                        System.out.println("Login succes");
                                        login=message.login;
                                        password=message.password;
                                        started = true;

                                    }
                                    if(message.typ== Message.MsgType.FileList){
                                        files=message.files;
                                    }
                                    try {
                                        while (in.isReadable()) {        // (1)

                                            System.out.flush();
                                        }
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        ReferenceCountUtil.release(msg); // (2)
                                    }


                                }

                                @Override
                                public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                                    ctx.fireChannelRegistered();
                                    chanell = ch;
                                    c = ctx.alloc().buffer();





                                    System.out.println("Channel registered");

                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws InterruptedException {
                                    ByteBuf msg = ctx.alloc().buffer();



                                    cause.printStackTrace();
                                    ctx.close();
                                }
                            });
                        }
                    });
            // (5)

            // Bind and start to accept incoming connections.
            System.out.println("Connecting...");
            ChannelFuture f = b.connect("localhost", port).sync(); // (7)
            System.out.println("Connection: stable");


            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
            System.out.println("Connection closed.");

            chanell=f.channel();

            //f.channel().writeAndFlush(c);
        } finally {
            group.shutdownGracefully();
        }

    }
    public void sendMSG(Message msg){
        Gson g = new GsonBuilder().create();
        String s = g.toJson(msg);
        if(msg.typ== Message.MsgType.FileUpload) {
            System.out.println("Sended: " + s);
        }
        ByteBuf buf = c.writeBytes(s.getBytes(StandardCharsets.UTF_8));

        byte bute[]= {2};
        String b="";
        for (int i = 0; i < buf.capacity(); i++) {
            bute[0] = buf.getByte(i);


            b=b+ new String(bute);
        }

        chanell.write(buf);
        chanell.flush();

    }
    public void reloadFileList(){

    }
}
