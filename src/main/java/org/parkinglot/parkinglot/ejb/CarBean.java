package org.parkinglot.parkinglot.ejb;

import com.parking.parkinglot.entities.Car;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.parkinglot.parkinglot.common.CarDto;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
@Stateless
public class CarBean {

    private static final Logger LOG = Logger.getLogger(CarBean.class.getName());
    @PersistenceContext
    private EntityManager entityManager;

    public List<CarDto> findAllCars() {
        LOG.info("findAllCars");

        try {
            TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    private List<CarDto> copyCarsToDto(List<Car> cars) {
        return cars.stream()
                .map(c -> new CarDto(
                        c.getId(),
                        c.getLicense_plate(),
                        c.getParkingSpot(),
                        c.getOwner().getUsername()   // sau getUsername(), getFullName(), etc. depinde din User
                ))
                .collect(Collectors.toList());
    }
}