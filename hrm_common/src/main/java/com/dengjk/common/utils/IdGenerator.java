package com.dengjk.common.utils;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class IdGenerator  implements Configurable,IdentifierGenerator {

    public String dataCenterID;
    public String workerId;

    public SnowflakeIdWorker g;

    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException{
        this.dataCenterID = params.getProperty("dataCenterID");
        this.workerId = params.getProperty("workerId");
        this.g=new SnowflakeIdWorker(Long.parseLong(workerId),Long.parseLong(dataCenterID));
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return g.nextId();
    }
}
