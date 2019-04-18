import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

/**
 * @author Dengjk
 * @create 2019-04-18 23:31
 * @desc
 **/
public class JwtCreateTest {


    /**
     * 创建jwt
     */
    @Test
    public void createJwt() {
        JwtBuilder jwtBuilder = Jwts.builder().setId("88").setSubject("邓先生").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "hrm88");
        String jwtToken = jwtBuilder.compact();
        System.out.println(jwtToken);
    }


    /**
     * 解析jwt
     */
    @Test
    public void parseJwt() {
        String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4OCIsInN1YiI6IumCk-WFiOeUnyIsImlhdCI6MTU1NTYwMjYzMn0.Ma-awJdzgnjVKrwt6acxYvrXbPeVFwSkrBT4LoeHdLM";
        Claims hrm = Jwts.parser().setSigningKey("hrm88").parseClaimsJws(jwtToken).getBody();
        String id = hrm.getId();
        System.out.println(hrm.getSubject());
    }




    /**
     * 创建jwt 自定义参数
     */
    @Test
    public void createJwt2() {
        JwtBuilder jwtBuilder = Jwts.builder().setId("88").setSubject("邓先生").setIssuedAt(new Date())
                .claim("name","dengxianshen")
                .claim("sex","男")
                .signWith(SignatureAlgorithm.HS256, "hrm88");
        String jwtToken = jwtBuilder.compact();
        System.out.println(jwtToken);
    }



    @Test
    public void parseJwt2() {
        String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4OCIsInN1YiI6IumCk-WFiOeUnyIsImlhdCI6MTU1NTYwMzA1OSwibmFtZSI6ImRlbmd4aWFuc2hlbiIsInNleCI6IueUtyJ9.i6Xv3AcDUXtj2L44tzuzdjABAG9egWDutY_j1dvSJYg";
        Claims hrm = Jwts.parser().setSigningKey("hrm88").parseClaimsJws(jwtToken).getBody();
        System.out.println((String) hrm.get("sex"));
        String id = hrm.getId();
        System.out.println(hrm.getSubject());
    }


}
