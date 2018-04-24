# generic-filter
Generic CRUD filter for Hibernate and Spring

Get a List of entities

```
@Autowired
private GenericService<EntityEx> genericService;
...
List<EntityEx> list = genericService.init(EntityEx.class).filteredList(new EntityExCriterionFilter());
...
```

### How to use with dynamic conditions with beans

You can use an object for build dynamic where conditions. Eg: in you API controller you're reciving some JSON payload, this JSON can be deserialized into a bean. Bean's attributes will be used as where conditions.

```
@Autowired
private GenericService<EntityEx> genericService;
...
EntityEx ex = this.deserializator(jsonString);
List<EntityEx> list = genericService.init(EntityEx.class).filteredList(ex);
...
```

### How to use ```EntityExCriterionFilter``` with static conditions

Add in the ```getFilter``` body conditions you want to use for filtering a table. Build your filter using ```CriterionFilterUtil``` class to add new ```CriterionFilterCondition``` use ```addCriterionFilterCondition()``` as shown in the example below

```
public class EntityExCriterionFilter implements CriterionFilter {

	public CriterionFilterUtil getFilter() {
		return new CriterionFilterUtil().addCriterionFilterCondition(new CriterionFilterCondition() {
			@Override
			public Criterion getCriterionFilterCondition(ObjectNode filters) {
				return Restrictions.eq("prop1", "123456");
			}
		}).addCriterionFilterCondition(new CriterionFilterCondition() {
			@Override
			public Criterion getCriterionFilterCondition(ObjectNode filters) {
				return Restrictions.eq("prop5", 444);
			}
		});
	}

}
```

Conditions defined in ```EntityExCriterionFilter``` in this case will be applyed as a where conditions.

## Use both together

```
@Autowired
private GenericService<EntityEx> genericService;
...
EntityEx ex = this.deserializator(jsonString);
List<EntityEx> list = genericService.init(EntityEx.class).filteredList(ex, new EntityExCriterionFilter());
...
```

Each property defined in the ```CriterionFilterCondition``` constructor will override the eventually condition set in the eventually EntityEx bean.

```
public class EntityExCriterionFilter implements CriterionFilter {

	public CriterionFilterUtil getFilter() {
		return new CriterionFilterUtil().addCriterionFilterCondition(new CriterionFilterCondition("prop1") {
			@Override
			public Criterion getCriterionFilterCondition(ObjectNode filters) {
				return Restrictions.eq("prop1", "123456");
			}
		}).addCriterionFilterCondition(new CriterionFilterCondition("prop5") {
			@Override
			public Criterion getCriterionFilterCondition(ObjectNode filters) {
				return Restrictions.eq("prop5", 444);
			}
		});
	}

}
```

