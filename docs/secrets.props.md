# secrets.props
in order to assert on sensitive info like emails and such `TestData` gets much of its integration-test data from 
`src/test/resources/secrets.props`.  
this file must contain values for certain keys in order to run the full integration test suite.
property keys were chosen to match the class within `TestData` and field that uses them (seemed the most intuitive approach).

## keys with digits
note, some keys contain digits to indicate groups of keys used to create groups of values.
some of these digit ranges are fixed and some are open-ended; some are 1-based and some 0-based.
likely these will be made more consistent tho certain ranges can't be open-ended if they're used to populate an enum such as 
`ColumnData.ColumnBriefData`.

## special key values (null vs empty string)
property values are all technically strings and if a key appears in `secrets.props` with no value its value will be `""`.
in order to have a `null` value for a prop it should be commented out.  
only some keys can support a `null` or empty value; most will trigger a test failure via
`assertThat(secrets.getProperty(name)).as("get(" + name + ")").isNotBlank()` 

[secrets.props.sample](/src/test/resources/secrets.props.sample) contains all the keys currently supported.

## pending improvements
* move ALL fields into secrets.props for easier adaptability by other users