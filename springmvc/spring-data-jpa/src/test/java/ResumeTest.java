import com.lagou.edu.dao.ResumeDao;
import com.lagou.edu.pojo.Resume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;


/**
 * @author ying
 * @version 1.0
 * @date 2021-05-06 22:53
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ResumeTest {

    @Autowired
    private ResumeDao resumeDao;

    @Test
    public void testFindById() {
        // 根据id查询
        Optional<Resume> resume = resumeDao.findById(1L);
        System.out.println(resume.get());
    }

    @Test
    public void testFindOne() {
        // 根据条件查询
        Resume resume = new Resume();
        resume.setId(1L);
        resume.setName("张三");
        Example<Resume> example = Example.of(resume);
        Optional<Resume> one = resumeDao.findOne(example);
        Resume resume1 = one.get();
        System.out.println(resume1);
    }

    @Test
    public void testSave() {
        // 新增和更新都使用save方法，通过传入的对象的主键有无来区分，没有主键信息那就是新增，有主键信息就是更新
        Resume resume = new Resume();
        resume.setId(5L);
        resume.setName("赵六");
        resume.setAddress("成都");
        resume.setPhone("132000000");
        Resume save = resumeDao.save(resume);
        System.out.println(save);
    }

    @Test
    public void testDelete() {
        // 根据id删除
        resumeDao.deleteById(5L);
    }

    @Test
    public void testFindByJpal() {
        List<Resume> resumes = resumeDao.findByJpal(1L, "张三");
        System.out.println(resumes);
    }

    @Test
    public void testFindBySql() {
        List<Resume> resumes = resumeDao.findBySql("张三", "北京");
        System.out.println(resumes);
    }

    @Test
    public void testFindByNameLikeAndAddress() {
        List<Resume> resumes = resumeDao.findByNameLikeAndAddress("张三", "北京");
        System.out.println(resumes);
    }

    // 动态查询，查询单个对象
    @Test
    public void testSpecfication() {

        /**
         * 动态条件封装
         * 匿名内部类
         *
         * toPredicate：动态组装查询条件
         *
         *      借助于两个参数完成条件拼装，，， select * from tb_resume where name='张三'
         *      Root: 获取需要查询的对象属性
         *      CriteriaBuilder：构建查询条件，内部封装了很多查询条件（模糊查询，精准查询）
         *
         *      需求：根据name（指定为"张三"）查询简历
         */
        Specification<Resume> specification = (Specification<Resume>) (root, criteriaQuery, criteriaBuilder) -> {
            // 获取到name属性
            Path<Object> name = root.get("name");
            // 使用CriteriaBuilder针对name属性构建条件（精准查询）
            Predicate predicate = criteriaBuilder.equal(name, "张三");
            return predicate;
        };
        Optional<Resume> optional = resumeDao.findOne(specification);
        Resume resume = optional.get();
        System.out.println(resume);
    }

    @Test
    public void testSpecficationMultiCon() {
        /**
         *  需求：根据name（指定为"张三"）并且，address 以"北"开头（模糊匹配），查询简历
         */
        Specification<Resume> specification = (Specification<Resume>) (root, criteriaQuery, criteriaBuilder) -> {
            // 获取到name属性
            Path<Object> name = root.get("name");
            Path<Object> address = root.get("address");
            // 条件1：使用CriteriaBuilder针对name属性构建条件（精准查询）
            Predicate predicate1 = criteriaBuilder.equal(name, "张三");
            // 条件2：address 以"北"开头（模糊匹配）
            Predicate predicate2 = criteriaBuilder.like(address.as(String.class), "北%");
            // 组合两个条件
            Predicate and = criteriaBuilder.and(predicate1, predicate2);
            return and;
        };

        Optional<Resume> optional = resumeDao.findOne(specification);
        Resume resume = optional.get();
        System.out.println(resume);
    }

    @Test
    public void testSort(){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        List<Resume> list = resumeDao.findAll(sort);
        for (Resume resume : list) {
            System.out.println(resume);
        }
    }

    @Test
    public void testPage(){
        /**
         * 第一个参数：当前查询的页数，从0开始
         * 第二个参数：每页查询的数量
         */
        Pageable pageable  = PageRequest.of(0,2);
        //Pageable pageable = new PageRequest(0,2);
        Page<Resume> all = resumeDao.findAll(pageable);
        System.out.println(all);
    }
}
