package com.digivisions.stc.entity;

import com.digivisions.stc.entity.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany
    @JoinColumn(referencedColumnName = "id")
//    @JsonManagedReference("parent")
    private Set<Item> items;

//    @ManyToOne
//    @JoinColumn(referencedColumnName = "id")
//    private Item parent;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Type type;

    @NotNull
    private String name;

    @JoinColumn(name = "permission_group_id")
//    @JsonBackReference("perm")
    @ManyToOne
    @Valid
    @NotNull
    private PermissionGroup permissionGroup;

    @JoinColumn(name = "file_id", unique = true)
    @OneToOne
    private File file;


}
