package com.easylinker.proxy.server.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    static BufferedReader BR = null;
    static long Count = 0;



    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        Trvs(new File("F:\\sucheon_project\\sucheon-master\\src\\main\\java\\com\\easylinker\\proxy\\server\\app"));
        System.out.print(Count);
    }



    static void Trvs(File f) throws IOException {
        File[] childs = f.listFiles();
        for (int i = 0; i < childs.length; i++) {
            if (childs[i].isFile()) {
                BR = new BufferedReader(new FileReader(childs[i]));
                while (BR.readLine() != null) Count += 1;
            } else Trvs(childs[i]);
        }
    }

}
