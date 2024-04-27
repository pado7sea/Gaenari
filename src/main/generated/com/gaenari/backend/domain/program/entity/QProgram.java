package com.gaenari.backend.domain.program.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProgram is a Querydsl query type for Program
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProgram extends EntityPathBase<Program> {

    private static final long serialVersionUID = -1665024467L;

    public static final QProgram program = new QProgram("program");

    public final NumberPath<Integer> duration = createNumber("duration", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final BooleanPath isFavorite = createBoolean("isFavorite");

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final ListPath<IntervalRange, QIntervalRange> ranges = this.<IntervalRange, QIntervalRange>createList("ranges", IntervalRange.class, QIntervalRange.class, PathInits.DIRECT2);

    public final NumberPath<Integer> setCount = createNumber("setCount", Integer.class);

    public final NumberPath<Integer> targetValue = createNumber("targetValue", Integer.class);

    public final StringPath title = createString("title");

    public final EnumPath<com.gaenari.backend.domain.program.dto.enumType.ProgramType> type = createEnum("type", com.gaenari.backend.domain.program.dto.enumType.ProgramType.class);

    public final NumberPath<Integer> usageCount = createNumber("usageCount", Integer.class);

    public QProgram(String variable) {
        super(Program.class, forVariable(variable));
    }

    public QProgram(Path<? extends Program> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProgram(PathMetadata metadata) {
        super(Program.class, metadata);
    }

}

