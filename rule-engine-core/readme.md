

```json
{
  "condition": {
    "type": "AND/OR/NOT",  // 逻辑运算符
    "field": "income",     // 直接字段判断时使用
    "operator": ">=",      // 比较运算符(>,<,=,exists等)
    "value": 50000,        // 比较值（可缺省）
    "conditions": [        // 嵌套子条件（当type为逻辑运算符时必须）
      {
        "type": "OR",
        "conditions": [
          { "field": "vipLevel", "operator": ">=", "value": 3 },
          { "field": "creditScore", "operator": ">=", "value": 700 }
        ]
      },
      {
        "field": "blacklistFlag",
        "operator": "==",
        "value": false
      }
    ]
  }
}
```