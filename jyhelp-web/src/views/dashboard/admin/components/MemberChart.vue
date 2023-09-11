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
      chartData: null,
      date: [],
      totalData: [],
      addData: []
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
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
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      this.date = []
      this.totalData = []
      this.addData = []
      dashboardApi.getMemberChart().then(res => {
        for (let i = 0; i < res.data.length; i++) {
          this.date.push(res.data[i].date)
          this.totalData.push({ value: res.data[i].total })
          this.addData.push({ value: res.data[i].add })
        }
        this.setOptions()
      })
    },
    setOptions() {
      this.chart.setOption({
        title: {
          text: '',
          x: 'center',
          y: 0,
          textStyle: {
            color: '#B4B4B4',
            fontSize: 16,
            fontWeight: 'normal'
          }
        },
        tooltip: {
          trigger: 'axis',
          backgroundColor: 'rgba(0,0,0,0.6)'
        },
        legend: {
          // 修改legend的高度宽度
          itemHeight: 5,
          itemWidth: 24,
          data: [
            {
              name: '注册会员数',
              icon: 'rect' // legend的icon
            },
            {
              name: '当日新增会员数',
              icon: 'rect'
            }
          ],
          textStyle: {
            color: '#B4B4B4'
          },
          top: '3%'
        },
        grid: {
          x: '8%',
          top: '12%',
          width: '85%',
          y: '5%'
        },
        xAxis: [
          {
            // type:'category',
            data: this.date,
            boundaryGap: true,
            axisLine: {
              lineStyle: {
                color: '#B4B4B4'
              }
            },
            axisTick: {
              show: false
            }
          }
        ],
        yAxis: [
          {
            name: '注册会员数',
            nameLocation: 'middle',
            nameTextStyle: {
              padding: [3, 4, 50, 6]
            },
            splitLine: {
              show: true,
              lineStyle: {
                type: 'dashed',
                color: '#eee'
              }
            },
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            axisLabel: {
              textStyle: {
                color: '#333'
              },
              formatter: '{value} '
            }
          },
          {
            name: '当日新增会员数',
            nameLocation: 'middle',
            nameTextStyle: {
              padding: [50, 4, 5, 6]
            },
            splitLine: {
              show: false
            },
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            axisLabel: {
              textStyle: {
                color: '#333'
              },
              formatter: '{value} '
            }
          }
        ],
        series: [
          {
            name: '注册会员数',
            type: 'line',
            smooth: true,
            showSymbol: true,
            // 矢量画五角星
            symbol: 'path://M150 0 L80 175 L250 75 L50 75 L220 175 Z',
            symbolSize: 12,
            yAxisIndex: 0,
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {
                    offset: 0,
                    color: 'rgba(250,180,101,0.3)'
                  },
                  {
                    offset: 1,
                    color: 'rgba(250,180,101,0)'
                  }
                ]),
                shadowColor: 'rgba(250,180,101,0.2)',
                shadowBlur: 20
              }
            },
            itemStyle: {
              normal: {
                color: '#FF8000'
              }
            },
            // data中可以使用对象，value代表相应的值，另外可加入自定义的属性
            data: this.totalData
          },
          {
            name: '当日新增会员数',
            type: 'bar',
            barWidth: 30,
            yAxisIndex: 1,
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {
                    offset: 0,
                    color: 'rgba(108,80,243,0.3)'
                  },
                  {
                    offset: 1,
                    color: 'rgba(108,80,243,0)'
                  }
                ]),
                // 柱状图圆角
                barBorderRadius: [30, 30, 0, 0]
              }
            },
            data: this.addData
          }
        ]
      })
    }
  }
}
</script>

<style scoped>

</style>
