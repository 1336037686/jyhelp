<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'
import dashboardApi from '@/api/module/dashboard/dashboard-api'
export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '300px'
    },
    autoResize: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      chart: null,
      date: [],
      orderCount: [],
      orderAmount: []
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.getData()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    getData() {
      dashboardApi.getOrderChart().then(res => {
        console.log(res.data)
        this.date = []
        this.orderCount = []
        this.orderAmount = []
        for (let i = 0; i < res.data.length; i++) {
          this.date.push(res.data[i].date)
          this.orderCount.push(res.data[i].count)
          this.orderAmount.push(res.data[i].amount)
        }
        this.initChart()
      })
    },
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      this.setOptions()
    },
    setOptions() {
      const colorList = ['#9E87FF', '#73DDFF', '#fe9a8b', '#F56948', '#9E87FF']
      this.chart.setOption({
        backgroundColor: '#fff',
        legend: {
          icon: 'circle',
          top: '5%',
          right: '5%',
          itemWidth: 6,
          itemGap: 20,
          textStyle: {
            color: '#556677'
          }
        },
        dataZoom: [
          {
            type: 'slider',
            show: true,
            realtime: true,
            start: 50,
            end: 90
          },
          {
            type: 'inside',
            realtime: true,
            start: 50,
            end: 90
          }
        ],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            label: {
              show: true
            },
            lineStyle: {
              width: 0
            }
          },
          padding: [10, 10],
          extraCssText: 'box-shadow: 1px 0 2px 0 rgba(163,163,163,0.5)'
        },
        grid: {
          left: '4%', // 左边距，可以是百分比或像素值
          right: '4%', // 右边距，同样可以是百分比或像素值
          top: '13%', // 上边距
          bottom: '20%'
        },
        xAxis: [
          {
            type: 'category',
            data: this.date,
            axisLine: {
              lineStyle: {
                color: '#DCE2E8'
              }
            },
            axisTick: {
              show: false
            },
            axisLabel: {
              interval: 0,
              textStyle: {
                color: '#556677'
              },
              // 默认x轴字体大小
              fontSize: 12,
              // margin:文字到x轴的距离
              margin: 10
            },
            axisPointer: {
              label: {
                // padding: [11, 5, 7],
                padding: [0, 0, 10, 0],
                // 这里的margin和axisLabel的margin要一致!
                margin: 10,
                // 移入时的字体大小
                fontSize: 12,
                backgroundColor: {
                  type: 'linear',
                  x: 0,
                  y: 0,
                  x2: 0,
                  y2: 1,
                  colorStops: [
                    {
                      offset: 0,
                      color: '#fff' // 0% 处的颜色
                    },
                    {
                      // offset: 0.9,
                      offset: 0.86,
                      color: '#fff' // 0% 处的颜色
                    },
                    {
                      offset: 0.86,
                      color: '#33c0cd' // 0% 处的颜色
                    },
                    {
                      offset: 1,
                      color: '#33c0cd' // 100% 处的颜色
                    }
                  ],
                  global: false // 缺省为 false
                }
              }
            },
            boundaryGap: false
          }
        ],
        yAxis: [
          {
            type: 'value',
            axisTick: {
              show: false
            },
            axisLine: {
              show: true,
              lineStyle: {
                color: '#DCE2E8'
              }
            },
            axisLabel: {
              textStyle: {
                color: '#556677'
              }
            },
            splitLine: {
              show: false
            }
          },
          {
            type: 'value',
            position: 'right',
            axisTick: {
              show: false
            },
            axisLabel: {
              textStyle: {
                color: '#556677'
              },
              formatter: '{value}'
            },
            axisLine: {
              show: true,
              lineStyle: {
                color: '#DCE2E8'
              }
            },
            splitLine: {
              show: false
            }
          }
        ],
        series: [
          {
            name: '当日订单数',
            type: 'line',
            data: this.orderCount,
            symbolSize: 1,
            symbol: 'circle',
            smooth: true,
            yAxisIndex: 0,
            showSymbol: false,
            lineStyle: {
              width: 5,
              color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                {
                  offset: 0,
                  color: '#9effff'
                },
                {
                  offset: 1,
                  color: '#9E87FF'
                }
              ]),
              shadowColor: 'rgba(158,135,255, 0.3)',
              shadowBlur: 10,
              shadowOffsetY: 20
            },
            itemStyle: {
              normal: {
                color: colorList[0],
                borderColor: colorList[0]
              }
            }
          },
          {
            name: '当日营收',
            type: 'line',
            data: this.orderAmount,
            symbolSize: 1,
            symbol: 'circle',
            smooth: true,
            yAxisIndex: 0,
            showSymbol: false,
            lineStyle: {
              width: 5,
              color: new echarts.graphic.LinearGradient(1, 1, 0, 0, [
                {
                  offset: 0,
                  color: '#73DD39'
                },
                {
                  offset: 1,
                  color: '#73DDFF'
                }
              ]),
              shadowColor: 'rgba(115,221,255, 0.3)',
              shadowBlur: 10,
              shadowOffsetY: 20
            },
            itemStyle: {
              normal: {
                color: colorList[1],
                borderColor: colorList[1]
              }
            }
          }
        ]
      })
    }
  }
}
</script>
