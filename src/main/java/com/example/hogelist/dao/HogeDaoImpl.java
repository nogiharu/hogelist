package com.example.hogelist.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.example.hogelist.common.Utils;
import com.example.hogelist.entity.HogeTable;
import com.example.hogelist.entity.HogeTable_;
import com.example.hogelist.form.HogeQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HogeDaoImpl implements HogeDao {
    private final EntityManager manager;

    @Override
    public Page<HogeTable> findByCriteria(Pageable pageable, HogeQuery hogeQuery) {
       // System.out.println(hogeQuery);
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<HogeTable> query = builder.createQuery(HogeTable.class);
        Root<HogeTable> root = query.from(HogeTable.class);
        List<Predicate> list = new ArrayList<>();

        // 名前
        String name = "%";
        if (hogeQuery.getName() != null && !hogeQuery.getName().equals("")) {
            name = "%" + hogeQuery.getName() + "%";
        }
        list.add(builder.like(root.get(HogeTable_.NAME), name));
        // 年齢
        if (hogeQuery.getAge() != null) {
            list.add(builder.and(builder.equal(root.get(HogeTable_.AGE), hogeQuery.getAge())));
        }
        // 性別
        if (hogeQuery.getGender() != null && !hogeQuery.getGender().equals("")) {
            list.add(builder.and(builder.equal(root.get(HogeTable_.GENDER), hogeQuery.getGender())));
        }
        // 誕生日開始～
        if (hogeQuery.getBirthdayFrom() != null) {
            list.add(builder
                    .and(builder.greaterThanOrEqualTo(root.get(HogeTable_.BIRTHDAY),
                            Date.valueOf(hogeQuery.getBirthdayFrom()))));
        }
        // 誕生日～終了
        if (hogeQuery.getBirthdayTo() != null) {
            list.add(builder.and(
                    builder.lessThanOrEqualTo(root.get(HogeTable_.BIRTHDAY), Date.valueOf(hogeQuery.getBirthdayTo()))));
        }

        // 登録日開始～
        if (!hogeQuery.getRegistrationDateFrom().equals("")) {
            list.add(builder.and(builder.greaterThanOrEqualTo(root.get(HogeTable_.REGISTRATION_DATE),
                    Utils.strDate(hogeQuery.getRegistrationDateFrom()))));
        }
        // 登録日～終了
        if (!hogeQuery.getRegistrationDateTo().equals("")) {
            list.add(builder.and(builder.lessThanOrEqualTo(root.get(HogeTable_.REGISTRATION_DATE),
                    Utils.strDate(hogeQuery.getRegistrationDateTo()))));
        }
        // 食べるか
        if (hogeQuery.getEat() != null) {
            list.add(builder.and(builder.equal(root.get(HogeTable_.EAT), hogeQuery.getEat())));
        }
        // 好きか
        if (hogeQuery.getVegetableLike() != null && !hogeQuery.getVegetableLike().equals("")) {
            list.add(builder.and(builder.equal(root.get(HogeTable_.VEGETABLE_LIKE), hogeQuery.getVegetableLike())));
        }
        // 趣味
        String hobby = "%";
        if (hogeQuery.getHobby() != null && !hogeQuery.getHobby().equals("")) {
            hobby = hogeQuery.getHobby();
        }
        list.add(builder.and(builder.like(root.get(HogeTable_.HOBBY), "%" + hobby + "%")));

        Predicate[] preArray = new Predicate[list.size()];
        list.toArray(preArray);
        query = query.select(root).where(preArray).orderBy(builder.asc(root.get(HogeTable_.ID)));

        // クエリ生成
        TypedQuery<HogeTable> typedQuery = manager.createQuery(query);
        // レコードの数
        int totalRows = typedQuery.getResultList().size();
        // 初期ページレコードの位置
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        // 一ページ当たりのサイズ
        typedQuery.setMaxResults(pageable.getPageSize());
       // System.out.println("[getPageSize]" + pageable.getPageSize());
       // System.out.println("[getPageNumber]" + pageable.getPageNumber());
       // System.out.println("[setFirstResult]" + typedQuery.getFirstResult());
       // System.out.println("[totalRows]" + totalRows);
        Page<HogeTable> hogeList = new PageImpl<>(typedQuery.getResultList(), pageable, totalRows);
        return hogeList;
    }
}